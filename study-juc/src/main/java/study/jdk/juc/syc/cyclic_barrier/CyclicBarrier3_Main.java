package study.jdk.juc.syc.cyclic_barrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 演示：循环栅栏
 */
public class CyclicBarrier3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrier3_Main.class);

    private static final int THREAD_NUM = 3;

    /**
     * 选手
     */
    private class Runner implements Runnable {

        private String name;

        private CyclicBarrier barrier;

        private Runner(String name, CyclicBarrier barrier) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            long time = Randoms.nextInt(10);
            LOGGER.info("runner[{}] sleep {}s", name, time);
            Sleeps.seconds(time);
            LOGGER.info("runner[{}] ready", name);
            try {
                barrier.await();
                LOGGER.info("runner[{}] done", name);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void test() {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread t1 = new Thread(new Runner(String.format("%s号选手", i + 1), barrier));
            t1.start();
        }
    }

    public static void main(String[] args) {
        new CyclicBarrier3_Main().test();
    }
}
