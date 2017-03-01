import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerProducer
{
    int rand() {
        return 1 + (int) (Math.random() * 3);
    }

    static boolean compareTwoInteger(int x, int y) {
        return !(((x % 2 == 0) ? true : false) ^ ((y % 2 == 0) ? true : false));
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
//        Producer producer = new Producer();
//        new Thread(producer).start();
//        Consumer consumer = new Consumer(producer);
//        new Thread(consumer).start();

        int countCore = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(countCore);
        Producer producer = new Producer();
        executorService.submit(producer);
        //new Thread(producer).start();

        for (int i = 0; i <= countCore - 1; ++i) {
            executorService.submit(new NamedConsumer("Consumer-" + (i + 2), producer));
        }

        //executorService.submit(new NamedConsumer("One", producer));
        //executorService.submit(new NamedConsumer("Two", producer));
//        NamedConsumer namedConsumer0 = new NamedConsumer("One", producer);
//        new Thread(namedConsumer0).start();
//        NamedConsumer namedConsumer1 = new NamedConsumer("Two", producer);
//        new Thread(namedConsumer1).start();
    }
}

class Consumer implements Runnable
{
    Producer producer;

    Consumer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            String message = producer.getMessage();
            System.out.println("Got message: " + message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

class NamedConsumer implements Runnable
{
    Producer producer;
    String name;

    NamedConsumer(String name, Producer producer) {
        this.name = name;
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            int numThread = Integer.parseInt(Character.toString(this.name.toCharArray()[this.name.length() - 1]));

            if (!ConsumerProducer.compareTwoInteger(numThread, Integer.parseInt(producer.showMessage())))
                continue;

            String message = producer.getMessage();
            System.out.println("Name: " + this.name + "; Got message: " + message);

            try {

                Thread.sleep(2500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

class Producer implements Runnable
{
    static final int MAXQUEUE = 5;
    private List<String> messages = new ArrayList<>();

    @Override
    public void run() {

        while (true) {
            try {
                putMessage(ConsumerProducer.class.newInstance().rand());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private synchronized void putMessage(int num) {
        while (messages.size() >= Producer.MAXQUEUE) {
            System.out.println("Max");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
        //System.out.println("Add");
        messages.add(/*" |№ " +*/ Integer.toString(num) /*+ "| " + new java.util.Date().toString()*/);
        messages.add(/*" |№ " +*/ Integer.toString(num) /*+ "| " + new java.util.Date().toString()*/);

        notifyAll();
    }

    public synchronized void cilce() {
        //while (messages.size() == 0) {
        while (messages.isEmpty()) {
            try {
                notifyAll();
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public synchronized String getMessage() {
        this.cilce();

        String message = (String)this.messages.remove(0);
        notifyAll();
        return message;

    }

    public synchronized String showMessage() {
        this.cilce();

        //notifyAll(); //Тут не надо, иначе при просмотре потоки будят всех остальных БЕСМЫСЛЕНО можно раскомитить и посмотреть
        //Видно через пару минут работы
        return (String)this.messages.get(0);

    }
}