package study.juc.thread.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

/**
 * 演示：中断普通方法
 */
public class Interrupt2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interrupt2_Main.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                LOGGER.info("loop");
            }
        });
        t.start();

        Sleeps.seconds(2);
        LOGGER.info("before interrupt= {}", t.isInterrupted());
        t.interrupt();
        LOGGER.info(" after interrupt= {}", t.isInterrupted());
    }
}
