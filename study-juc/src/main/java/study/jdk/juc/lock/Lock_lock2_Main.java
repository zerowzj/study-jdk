package study.jdk.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示：阻塞获取锁且不可中断
 */
public class Lock_lock2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lock_lock2_Main.class);

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                LOGGER.info("i am thread jmap");
                Sleeps.deep();
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                LOGGER.info("i am thread t2 ");
            } finally {
                lock.unlock();
            }
        });

        //t1持有锁，t2阻塞于该锁
        t1.start();
        Sleeps.seconds(1);
        t2.start();
        //主线程中断t2，t2无响应
        Sleeps.seconds(3);
        t2.interrupt();
    }
}
