package study.jdk.juc.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示：线程池执行器
 * （1）
 */
public class ThreadPoolExecutor1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutor1_Main.class);

    public static final int TASK_NUM = 10;

    /**
     * 线程工厂
     */
    private class MyThreadFactory implements ThreadFactory {

        private AtomicInteger tNo = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Thread-" + tNo.getAndIncrement());
            return t;
        }
    }

    /**
     * 拒绝策略
     */
    private class MyPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LOGGER.info("discard");
        }
    }

    private void test() {
        //核心线程池数量
        int corePoolSize = 2;
        //最大线程池数量
        int maximumPoolSize = 3;
        //阻塞任务队列
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);
        //线程池中超过corePoolSize数目的空闲线程最大存活时间
        int keepAliveTime = 0;
        //线程创建工厂
        ThreadFactory factory = new MyThreadFactory();
        //当提交任务数超过maximumPoolSize + workQueue之和时，任务会交给RejectedExecutionHandler来处理
        RejectedExecutionHandler handler = new MyPolicy();

        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                workQueue,
                factory,
                handler);
        LOGGER.info("i am main thread");
        for (int i = 0; i < TASK_NUM; i++) {
            int taskNo = i + 1;
            pool.execute(() -> {
                int random = 5;
                LOGGER.info("i am task[{}], sleep {}s", taskNo, random);
                Sleeps.seconds(random);
                LOGGER.info("task[{}] end", taskNo);
            });
        }
        pool.shutdown();
        LOGGER.info("main thread end");
    }

    public static void main(String[] args) {
        new ThreadPoolExecutor1_Main().test();
    }
}
