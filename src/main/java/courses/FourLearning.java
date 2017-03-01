package courses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by arxemond777 on 30.01.17.
 */
public class FourLearning
{
    public static void main(String[] args) {
        String [] a = new String[10];
        a[0] = "a";
        //b(a);

        d();

    }

    static String c() {
        String path = "/";

        /**
         * BufferedReader реализует интерфес AutoCloseable
         * поэтому не надо в finaly делать br.close()
         */
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            return br.readLine();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            return "";
        }
    }

    static void d() {
        try {
            try {
                System.out.println("a");
                throw new IOException("a");
            } finally {
                if (true) {
                    System.out.println("b");
                    throw new Exception("b");//вот это перебивает летящий IOException и уходит в Exception
                }
                System.out.println("c");
            }
//            throw new IOException("a");
        } catch (IOException e) {
            System.out.println("d");
        } catch (Exception e) {
            System.out.println("e");
//            throw new Exception("1");
        }
    }

    static void b(String [] ab) {
        Object obj = null;

        try {
            obj = new Object();
            a(ab);
        } catch (NumberFormatException e) {
//            for (StackTraceElement err :e.getStackTrace())
//                System.out.println(err);
            e.printStackTrace();

//            if (obj != null)
//                System.out.println(obj);

        }
    }

    static void a(String [] ab) throws NumberFormatException {
        //try {
            Integer count = Integer.parseInt(ab[0]);
            System.out.println(count);
        //} catch (NumberFormatException e) {

        //}
    }
}
