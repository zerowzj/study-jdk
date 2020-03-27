package study.jdk.juc.pool.thread_pool_executor;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Sleeps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示：
 * （1）线程池参数：核心线程数、最大线程数、线程工厂、队列、拒绝策略
 * （2）线程池基本使用：创建、使用、关闭
 * （3）线程拒绝策略：maximumPoolSize + workQueue.size
 */
@Slf4j
public class ThreadPoolExecutor1_Main {

    //线程工厂
    class MyThreadFactory implements ThreadFactory {

        private AtomicInteger tNo = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "POOL-NAME-THREAD-" + tNo.getAndIncrement());
            return t;
        }
    }

    //拒绝策略
    class MyPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("discard");
        }
    }

    private void demo() {
        int taskNum = 10;

        //（★）参数
        //（1）核心线程池数量
        int corePoolSize = 2;
        //（2）最大线程池数量
        int maximumPoolSize = 3;
        //（3）线程池中超过corePoolSize数目的空闲线程最大存活时间
        int keepAliveTime = 0;
        //（4）时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //（5）阻塞任务队列
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);
        //（6）线程创建工厂
        ThreadFactory factory = new MyThreadFactory();
        //（7）当提交任务数超过maximumPoolSize + workQueue之和时，任务会交给RejectedExecutionHandler来处理
        RejectedExecutionHandler handler = new MyPolicy();
        //（★）创建
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime, unit,
                workQueue,
                factory,
                handler);
        log.info("i am main thread");
        for (int i = 0; i < taskNum; i++) {
            int taskNo = i + 1;
            //（★）执行
            pool.execute(() -> {
                int random = 5;
                log.info("i am task[{}], sleep {}s", taskNo, random);
                Sleeps.seconds(random);
                log.info("task[{}] end", taskNo);
            });
        }
        //（★）关闭
        pool.shutdown();
        log.info("main thread end");
    }

    public static void main(String[] args) {
        new ThreadPoolExecutor1_Main().demo();
    }
}
