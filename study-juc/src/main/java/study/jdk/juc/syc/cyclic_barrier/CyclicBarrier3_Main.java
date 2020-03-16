package study.jdk.juc.syc.cyclic_barrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 演示：
 */
@Slf4j
public class CyclicBarrier3_Main {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            log.info("i am barrierAction thread");
        });

        Thread t = new Thread(() -> {
            try {
                barrier.await();
                log.info("i am t thread");
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
        log.info("i am main thread");
    }
}
