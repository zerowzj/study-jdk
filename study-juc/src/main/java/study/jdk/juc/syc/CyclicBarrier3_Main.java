package study.jdk.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 演示：
 */
public class CyclicBarrier3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrier3_Main.class);

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            LOGGER.info("i am barrierAction thread");
        });

        Thread t = new Thread(() -> {
            try {
                barrier.await();
                LOGGER.info("i am t thread");
            } catch (InterruptedException ex) {
            } catch (BrokenBarrierException ex) {
            }
        });
        t.start();

        try {
            barrier.await();
        } catch (InterruptedException ex) {
        } catch (BrokenBarrierException ex) {
        }
        LOGGER.info("i am main thread");
    }
}
