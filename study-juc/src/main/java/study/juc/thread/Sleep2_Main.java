package study.juc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

/**
 * 演示：
 */
public class Sleep2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sleep2_Main.class);

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            //t1并不持有锁
            Sleeps.seconds(10);
            LOGGER.info("i am thread jmap");
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("i am thread t2");
            }
        });

        t1.start();
        Sleeps.seconds(1);
        t2.start();
    }
}
