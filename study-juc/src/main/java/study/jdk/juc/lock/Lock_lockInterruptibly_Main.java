package study.jdk.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示：阻塞获取锁且可中断
 */
public class Lock_lockInterruptibly_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lock_lockInterruptibly_Main.class);

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                LOGGER.info("i am thread jmap");
                Sleeps.deep();
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException ex) {
                //中断时的逻辑
                LOGGER.info("i am interrupted");
            } finally {
                LOGGER.info("======> code in finally");
                lock.unlock();
            }
            LOGGER.info("======> code after finally ");
        });

        //t1持有锁，t2阻塞于该锁
        t1.start();
        Sleeps.seconds(1);
        t2.start();
        //主线程中断t2，t2抛出InterruptedException
        Sleeps.seconds(3);
        t2.interrupt();
    }
}
