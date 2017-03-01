package courses.Eleven;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleThread
{
    static Logger log = LoggerFactory.getLogger(Producer.class);

    static List<String> list = new ArrayList<>();

    static class Producer extends Thread
    {
        private final Object lock;

        Producer(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                log.info("[Producer] waiting data...");

                sleep(2000);

                System.out.println("Processing data");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("[Producer] data prepared");

            synchronized (lock) {
                list.add("Ready");
                lock.notifyAll();
            }
        }
    }

    static class Consumer extends Thread
    { //Thread A (с листка) - поток ожидающий данные
        private final Object lock;
        Consumer(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (this.lock) {
                try {
                    log.info("[Consumer] wait for log");

                    while (!list.isEmpty()) {
                        lock.wait();
                    }

                    System.out.println("Processing data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("[Consumer] data received");
            }
        }
    }

    public static void main(String[] args) {
        Object lock = new Object(); //Monitor
        new Consumer(lock).start();
        new Producer(lock).start();
    }
}
