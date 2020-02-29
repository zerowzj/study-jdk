package study.java.juc.thread.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Sleeps;

/**
 * 演示：中断
 */
public class Interrupt1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interrupt1_Main.class);

    private Object lock = new Object();

    void test() {
        Thread t = new Thread(() -> {
            try {
                synchronized (lock) {
                    LOGGER.info("i am waiting");
                    lock.wait();
                }
            } catch (InterruptedException ex) {
                LOGGER.info("i am interrupted");
            }
            LOGGER.info("after interrupt");
        });
        t.start();

        Sleeps.seconds(2);
        t.interrupt();
        LOGGER.info("interrupt over");
    }

    public static void main(String[] args) {
        new Interrupt1_Main().test();
    }
}
