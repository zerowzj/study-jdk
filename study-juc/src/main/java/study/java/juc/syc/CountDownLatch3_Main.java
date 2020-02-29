package study.java.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Sleeps;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示：
 */
public class CountDownLatch3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            pool.execute(new Task(latch, i));
        }

        try {
            latch.await();
        } catch (InterruptedException ex) {
        }
        LOGGER.info("等待线程被唤醒！");
        pool.shutdown();
    }

    private static class Task implements Runnable {

        private final CountDownLatch latch;

        private final int tid;

        public Task(CountDownLatch latch, int tid) {
            this.latch = latch;
            this.tid = tid;
        }

        @Override
        public void run() {
            LOGGER.info("线程{}完成了操作", tid);
            Sleeps.seconds(4);
            latch.countDown();
        }
    }
}
