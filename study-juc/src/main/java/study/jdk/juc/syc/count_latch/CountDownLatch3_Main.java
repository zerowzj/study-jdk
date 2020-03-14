package study.jdk.juc.syc.count_latch;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示：
 */
@Slf4j
public class CountDownLatch3_Main {

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
        log.info("等待线程被唤醒！");
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
            log.info("线程{}完成了操作", tid);
            Sleeps.seconds(4);
            latch.countDown();
        }
    }
}
