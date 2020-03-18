package study.jdk.juc.syc.countdown_latch;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Sleeps;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示：
 * （1）线程池中使用latch
 * （2）任务持有latch引用进行倒数
 */
@Slf4j
public class CountDownLatch2_Main {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        int count = 10;
        //（★-1）初始化
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            pool.execute(new CountDownLatch2_Main().new Task(latch, i));
        }

        try {
            //（★-3）集合点
            latch.await();
        } catch (InterruptedException ex) {

        }
        log.info("等待线程被唤醒！");
        pool.shutdown();
    }

    class Task implements Runnable {

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
            //（★-2）倒数
            latch.countDown();
        }
    }
}
