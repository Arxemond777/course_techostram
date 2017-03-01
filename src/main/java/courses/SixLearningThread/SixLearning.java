package courses.SixLearningThread;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by arxemond777 on 02.02.17.
 */
public class SixLearning
{
    public static void main(String[] args) throws Exception {
        AtomicCounter counter = new SixLearning().new AtomicCounter();

        int threadNum = 2;
        Thread thread;
        Thread[] threads = new Thread[threadNum];
        for(int i = 0; i < threadNum; ++i){
                thread = new SixLearning().new Test(counter);
                threads[i] = thread;
                thread.start();
        }

        for (Thread t : threads)
            t.join();

        System.out.printf("ThreadNum: %d\nNum: %d", threadNum, counter.getInc());
//        System.out.println(counter.getInc());

    }

    class Test extends Thread
    {
        private final Object countLock = new Object();

        private AtomicCounter counter;

        Test(AtomicCounter counter) {this.counter = counter;}

        public void run() {
            for (int i = 0; i < 100_000; ++i) {
                //synchronized (counter) {//рабочий
//                Lock lock = new ReentrantLock(true);//не рабочий. Сделать поток честный, если пришли 1, 2, 3 потоки то так и обработаются
//                lock.lock();
                    counter.inc();
                    //inc();
//                lock.unlock();
                //}
            }

        }

        public long inc() {
            synchronized (countLock) {//Не всегда работает
                return counter.inc();
            }
        }
    }

    class AtomicCounter
    {
        private volatile long val;//потому что на 32 может быть подругому

        public  long inc() {
            return  val++;
        }

        public long getInc() {return val;}

//        private AtomicLong val = new AtomicLong(0);
//
//        /** Через атомарные **/
//        public /*synchronized*/ long inc() {
//            return  val.getAndIncrement();
//        }
//
//        public long getInc() {return val.get();}
    }


}
