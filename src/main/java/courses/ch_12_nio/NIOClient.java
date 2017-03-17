package courses.ch_12_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.OP_CONNECT;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class NIOClient
{
    static final int PORT = 10050;
    static final String ADDRESSS = "localhost";
    private ByteBuffer byteBuffer = allocate(16);

    private void run() throws Exception {
        SocketChannel channel = SocketChannel.open(); //Создали канал, но еще НЕ ПОДКЛЮЧИЛИСЬ
        channel.configureBlocking(false); //Не блокирующий канал (по дефолту и так false)
        Selector selector = Selector.open(); //Созадли селлектор

        /**
         * Дружим селектор и канал.
         * OP_CONNECT - как запустят, войди в режим ожидания, что к тебе будут подключаться
         */
        channel.register(selector, OP_CONNECT); //Не явно порадил select на подклчение (OP_CONNECT)

        channel.connect(new InetSocketAddress(ADDRESSS, PORT)); //Подключение к сети. Тут первое действие, когда канал РЕАЛЬНО соединился

        /**
         * Не большая блокирующиеся очередь
         * Класться может с ВСЕГДА только с одного потока, но читаться с нескольких
         * <String> - потому что читаем с консоли, а там стринг
         */
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        /**
         * До этого мы работали с селектором в main-нити, т.к. вызван run
         */
        new Thread(() -> { //А тут создаем новый поток работающий с блокирующимся сканнером
            /**
             * Так же можно класть в очередь и тогда consumer их будет их обрабатывать
             */
            Scanner scanner = new Scanner(System.in); //Считывается с консоли

            while (true) {
                String line = scanner.nextLine(); //Тут блокируемся
                if ("q".equals(line))
                    System.exit(0);

                try {
                    queue.put(line); //Отравка на сервере, если не команда выхода "q".equals(line)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * Чтоб отправить, нам нужен selector
                 * Есть какнал и селектор, который мы до этого подружили
                 * На прямую с каналом работать нельзя, поэтому говорим через селектор:
                 * "Канал, я хочу тебя использовать на запись key.interestOps(OP_WRITE)"
                 * Дай нам твой селектор
                 */
                SelectionKey key = channel.keyFor(selector); //Канал создай этот селекшенкей, используя этот селекшен
                key.interestOps(OP_WRITE); //Создан на запись
                selector.wakeup(); //Будим селектор, он спал на ctrl+F TODO select sleep
            }
        }).start(); //Запуск в отдельной нити, который работает с selector-блокирующимся

        while (true) { //Поток в main
            /**
             * TODO select sleep - тут спит. Когда нефиг делать, и не работаем ни на одном канале, с которым
             * связан этот селектор
             * как только положили в очередь событие и создаем Event
             * SelectionKey key = channel.keyFor(selector);
             * key.interestOps(OP_WRITE); //Создан на запись
             * selector.wakeup(); - будим его
             */
            selector.select();

            for (SelectionKey selectionKey : selector.selectedKeys()) { //Когда проснулся, то получаем события, которые произошли, пока спал selection
                if (selectionKey.isConnectable()) { //Ты кто, событие на подключится?
                    channel.finishConnect(); //Завершить процесс подключЕНИЯ, а не закрыть его

                    /**
                     * Когда подключились, то стали готовы на запись. Как ток подключились, то будем писать в поток SELECTION - открытый выше
                     */
                    selectionKey.interestOps(OP_WRITE);
                } else if (selectionKey.isReadable()) { //TODO 2
                    byteBuffer.clear(); //На всякий случай очищаем буфер. Точнее его курсор, ссылку на начало
                    channel.read(byteBuffer); //Канал, прочти данные из буфера
                    System.out.println("Recieved = " + new String(byteBuffer.array()));
                } else if (selectionKey.isWritable()) {
                    String line = queue.poll(); //Достали строчку, которую записали

                    if (line != null) //Если строка не пустая
                        channel.write( //Записали в массив, в виде буфера
                                ByteBuffer.wrap(
                                        line.getBytes() //Строка в массив байт
                                )
                        );

                    selectionKey.interestOps(OP_READ); //Ключ ожидающий чтение, идет в TODO 2
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new NIOClient().run();
    }
}
