package courses.NineLearningSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client
{
    public static final int PORT = 19_000;
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        System.out.println("Start");
        new Thread(new Client().new SendRequest()).start();
        System.out.println("Stop");
    }

    class SendRequest implements Runnable
    {
        Socket socket = null;

        @Override
        public void run() {
            try {
                socket = new Socket(HOST, PORT);

                try(
                        InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream()
                ) {


                    String line = "Hello";
                    out.write(line.getBytes());
                    out.flush();

                    byte[] data = new byte[32 * 1024];
                    int readBytes = in.read(data);

                    System.out.printf("Server> %s \n", new String(data, 0, readBytes));
                }
            } catch (IOException e) {

            } finally {
                IoUtil.closeQuietly(socket);
            }
        }
    }

}
