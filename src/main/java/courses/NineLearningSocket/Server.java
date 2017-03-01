package courses.NineLearningSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Server
{
    public static final int PORT = 19_000;

    public static void main(String[] args) {
        Server server = new Server();
        while (true) {
            // Не лучший
            server.main1();
        }
    }

    void main1() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Start, waiting for connect " + new Date());

            Socket socket = serverSocket.accept();

            ExecutorService es = Executors.newFixedThreadPool(10);
            es.submit(new Server().new AsyncServer(socket));
//            Future f = es.submit(new Server().new AsyncServer(socket));
//
//            List<Future> fs = new ArrayList<>();
//            fs.add(f);
//
//            try {
//                for (Future fVal : fs) {
//                    System.out.println(1);
//                    fVal.get();
//                }
//            } catch (InterruptedException e) {
//                //System.out.println(e.getMessage());
//                e.printStackTrace(System.err);
//            } catch (ExecutionException e) {
//                System.out.println(e.getMessage());
//            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            IoUtil.closeQuietly(serverSocket);
        }
    }

    class AsyncServer implements Runnable
    {
        Socket socket = null;

        public AsyncServer(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            System.out.println("Accepted. " + socket.getInetAddress());


            try(
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream()
            ) {
                byte[] buf = new byte[32 * 1024];
                int readBytes = in.read(buf);
                String line = new String(buf, 0, readBytes);
                System.out.printf("Client> %s", line);

                out.write(line.getBytes());
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
