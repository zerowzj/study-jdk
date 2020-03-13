package study.jdk.juc.pool.fixed_thread_pool;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示：固定线程池
 * （1）任务异步执行，无临界区
 * （2）ThreadFactory 用法
 */
@Slf4j
public class FixedThreadPool1_Main {

    private static final int THREAD_NUM = 3;

    private static final int TASK_NUM = 5;

    /**
     * 线程工厂
     */
    private class MyThreadFactory implements ThreadFactory {

        private AtomicInteger tNo = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "pool-thread-" + tNo.getAndIncrement());
            return t;
        }
    }

    private void test() {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM, new MyThreadFactory());
        //线程池pool执行
        for (int i = 0; i < TASK_NUM; i++) {
            int no = i + 1;
            pool.execute(() -> {
                int random = Randoms.nextInt(10);
                log.info("i am task [{}], sleep {}s", no, random);
                Sleeps.seconds(random);
                log.info("task [{}] end", no);
            });
        }
        pool.shutdown();
    }

    public static void main(String[] args) {
        new FixedThreadPool1_Main().test();
    }
}
