package study.jdk.juc.syc.countdown_latch;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Randoms;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RunningRace {

    //选手
    static class Runner implements Runnable {

        private CountDownLatch latch;

        public Runner(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                int millSec = Randoms.nextInt(1000);
                TimeUnit.MILLISECONDS.sleep(millSec);
                log.info("{} 选手已就位, 准备共用时：{} ms", Thread.currentThread().getName(), millSec);
            } catch (InterruptedException ex) {

            } finally {
                //准备完毕，举手示意
                latch.countDown();
            }
        }
    }

    private static final int THREAD_SIZE = 10;

    private static final int RUNNER_SIZE = 20;

    private static final int LATCH_CNT = 5;

    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_SIZE,
                20,
                1000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                (r) -> new Thread(r, counter.addAndGet(1) + "号"),
                new ThreadPoolExecutor.AbortPolicy());

        CountDownLatch latch = new CountDownLatch(LATCH_CNT);
        for (int i = 0; i < RUNNER_SIZE; i++) {
            pool.execute(new Runner(latch));
        }

        //裁判等待5名选手准备完毕，为了避免死等，也可以添加超时时间
        try {
            latch.await();
        } catch (InterruptedException ex) {

        }
        log.info("裁判：比赛开始~~");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {

        }
        pool.shutdownNow();
    }
}
