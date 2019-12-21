package study.juc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 等待/通知
 */
public class WaitNotify1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitNotify1_Main.class);

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("A 1");
                //等待
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                LOGGER.info("A 2");
                LOGGER.info("A 3");
            }
        }, "jmap");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("B 1");
                LOGGER.info("B 2");
                LOGGER.info("B 3");
                //通知
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
