package courses.ch_12_nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.*;

import java.nio.ByteBuffer;

public class First
{
    public static void main(String[] args) throws IOException {

    }

    public static void A() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/tmp/foo.txt", "rw");
        FileChannel channel = aFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);

        while (bytesRead != -1) {

            buffer.flip();  //make buffer ready for read

            while(buffer.hasRemaining()){

                System.out.print((char) buffer.get()); // read 1 byte at a time
            }

            buffer.clear(); //make buffer ready for writing
            bytesRead = channel.read(buffer);
        }

        buffer.clear(); //Сброс курсора
    }

    public static void B() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        channel.configureBlocking(false); //неблокирующийся
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
    }
}
