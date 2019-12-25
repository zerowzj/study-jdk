package study.juc.thread.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

import java.util.concurrent.TimeUnit;

/**
 * 演示：中断阻塞方法
 */
public class Interrupt3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interrupt3_Main.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                LOGGER.info("middle interrupt= {}", Thread.currentThread().isInterrupted());
            }
        });
        t.start();

        LOGGER.info("before interrupt= {}", t.isInterrupted());
        Sleeps.seconds(1);
        t.interrupt();
//        Sleeps.seconds(1);
        LOGGER.info(" after interrupt= {}", t.isInterrupted());
    }
}
