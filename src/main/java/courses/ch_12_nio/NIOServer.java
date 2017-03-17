package courses.ch_12_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class NIOServer
{
    private Selector selector;
    private ByteBuffer readBuffer = allocate(8192);
    private EchoWorker worker = new EchoWorker();
    static final int PORT = 10050;
    static final String ADDRESSS = "localhost";
    static final int CHANGEGEOPS = 1;

    private final List<ChangeRequest> changeRequests = new LinkedList<>();
    private final Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<>();

    private NIOServer() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(ADDRESSS, PORT);
        serverChannel.socket().bind(isa); //Создаем сокет и он начинает слушать адресс указанный выше
        selector = SelectorProvider.provider().openSelector();
        serverChannel.register(selector, OP_ACCEPT); // OP_ACCEPT - на прием, а в клиенте OP_CONNECT на подключение
        new Thread(worker).start();
    }

    public static void main(String[] args) throws IOException {
        new NIOServer().run();
    }

    private void run() throws IOException {
        while (true) {
            synchronized (changeRequests) {
                for (ChangeRequest changeRequest : changeRequests) {
                    switch (changeRequest.type()) {
                        case CHANGEGEOPS:
                            SelectionKey key = changeRequest.socket.keyFor(selector);
                            key.interestOps(changeRequest.ops);
                            break;
                        default:
                    }
                }
                changeRequests.clear();
            }

            selector.select(); //Тоже блочимся, пока не подключатся
            Iterator<SelectionKey> selectionKey = selector.selectedKeys().iterator(); //Дай все Event пока я стоял
            while (selectionKey.hasNext()) {
                SelectionKey key = selectionKey.next();
                selectionKey.remove();
                if (!key.isValid())
                    continue;

                if (key.isAcceptable())
                    accept(key);
                else if (key.isReadable())
                    readKey(key);
                else if (key.isWritable())
                    write(key);
            }

        }
    }

    public void send(SocketChannel socketChannel, byte[] data) {
        synchronized (changeRequests) {
            changeRequests.add(new ChangeRequest(socketChannel, CHANGEGEOPS, OP_WRITE));
            synchronized (pendingData) {
                List<ByteBuffer> queue = pendingData.get(socketChannel);
                if (queue == null) {
                    queue = new ArrayList<>();
                    pendingData.put(socketChannel, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }
        selector.wakeup();
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel(); //key - связывает канал с селектором
        SocketChannel socketChannel = serverSocketChannel.accept(); //Принимаем его
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, OP_READ);
    }

    private void readKey(SelectionKey key) throws IOException {
        SocketChannel SocketChannel = (SocketChannel) key.channel();
        readBuffer.clear(); //Подготовили буффер
        int numRead = SocketChannel.read(readBuffer); //Прочли байтики в этот буфер
        worker.processData(this, SocketChannel, readBuffer.array(), numRead);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (pendingData) {
            List<ByteBuffer> queue = pendingData.get(socketChannel);

            while (!queue.isEmpty()) {
                ByteBuffer buf = queue.get(0);
                socketChannel.write(buf);

                if (buf.remaining() > 0)
                    break;

                System.out.println("Send echo = " + new String(queue.get(0).array()));
                queue.remove(0);
            }
            if (queue.isEmpty())
                key.interestOps(OP_READ);
        }
    }

}
