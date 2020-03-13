package study.jdk.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock1_Main {

    private static int sum = 0;

    private static final int TIME = 5000;

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Lock1_Main main = new Lock1_Main();
        Thread t1 = new Thread(() -> {
//            main.count();
            main.safeCount();
        });
        Thread t2 = new Thread(() -> {
//            main.count();
            main.safeCount();
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("sum= " + sum);
    }

    private void count() {
        for (int i = 0; i < TIME; i++) {
            sum++;
        }
    }

    private void safeCount() {
        lock.lock();
        try {
            for (int i = 0; i < TIME; i++) {
                sum++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
