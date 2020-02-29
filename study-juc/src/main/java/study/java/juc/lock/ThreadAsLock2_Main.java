package study.java.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Sleeps;

/**
 * （1）普通对象作为锁
 */
public class ThreadAsLock2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadAsLock2_Main.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            LOGGER.info("i am lock thread, sleep 10s");
            Sleeps.seconds(10);
        });
        t.start();

        Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait(15 * 1000);
                LOGGER.info("i am main thread");
            } catch (InterruptedException ex) {
            }
        }
    }
}
