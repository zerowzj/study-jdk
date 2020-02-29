package study.java.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Sleeps;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示：非阻塞获取锁
 */
public class Lock_tryLock_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lock_tryLock_Main.class);

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                LOGGER.info("i am thread jmap ");
                Sleeps.deep();
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            if (lock.tryLock()) {
                try {

                } finally {
                    lock.unlock();
                }
            } else {
                LOGGER.info("not get lock");
            }
        });

        //t1持有锁，t2非阻塞获取锁
        t1.start();
        Sleeps.seconds(1);
        t2.start();
    }
}
