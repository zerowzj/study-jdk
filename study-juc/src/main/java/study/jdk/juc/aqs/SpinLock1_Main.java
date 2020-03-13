package study.jdk.juc.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

/**
 * 演示：
 * （1）
 */
public class SpinLock1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpinLock1_Main.class);

    public static void main(String[] args) {
        SpinLock lock = new SpinLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                LOGGER.info("i am jmap thread");
                Sleeps.seconds(5);
                LOGGER.info("jmap thread end");
            } finally {
                lock.unlock();
            }
        }, "jmap");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                LOGGER.info("i am t2 thread");
                Sleeps.seconds(5);
                LOGGER.info("t2 thread end");
            } finally {
                lock.unlock();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                LOGGER.info("i am t3 thread");
                LOGGER.info("t3 thread end");
            } finally {
                lock.unlock();
            }
        }, "t3");

        t1.start();
        Sleeps.seconds(1);
        t2.start();
        t3.start();
        LOGGER.info("main thread end");
    }
}
