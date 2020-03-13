package study.jdk.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 演示：
 */
public class CyclicBarrier2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrier2_Main.class);

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);
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
