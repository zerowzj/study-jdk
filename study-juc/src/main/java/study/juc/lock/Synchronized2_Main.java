package study.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

/**
 * 演示：synchronized不可中断
 */
public class Synchronized2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Synchronized2_Main.class);

    private Object lock = new Object();

    void test() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("i am thread jmap");
                Sleeps.seconds(20);
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("i am thread t2");
            }
        });
        t1.start();
        Sleeps.seconds(1);
        t2.start();

        //t1持有锁，t2阻塞于该锁，main中断t2
        Sleeps.seconds(3);
        t2.interrupt();
    }

    public static void main(String[] args) {
        new Synchronized2_Main().test();
    }
}
