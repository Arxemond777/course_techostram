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
    static final int PORT = 9000;
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
        channel.register(selector, OP_CONNECT);

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
                String line = scanner.nextLine();
                if ("q".equals(line))
                    System.exit(0);

                try {
                    queue.put(line);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SelectionKey key = channel.keyFor(selector);
                key.interestOps(OP_WRITE);
                selector.wakeup();
            }
        }).start();

        while (true) {
            selector.select();
            for (SelectionKey selectionKey : selector.selectedKeys()) {
                if (selectionKey.isConnectable()) {
                    channel.finishConnect();
                    selectionKey.interestOps(OP_WRITE);
                } else if (selectionKey.isReadable()) {
                    byteBuffer.clear();
                    channel.read(byteBuffer);
                    System.out.println("Recieved = " + new String("666"));
                } else if (selectionKey.isWritable()) {
                    String line = queue.poll();

                    if (line != null) {
                        channel.write(ByteBuffer.wrap(line.getBytes()));
                    }
                    selectionKey.interestOps(OP_READ);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new NIOClient().run();
    }
}
