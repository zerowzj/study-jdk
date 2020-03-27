package study.jdk.juc.pool.thread_pool_executor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Sleeps;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示：
 * （1）
 */
@Slf4j
public class ThreadPoolExecutor2_Main {

    //任务
    @Getter
    private class Task implements Runnable {

        private String taskNo;

        public Task(String taskNo) {
            this.taskNo = taskNo;
        }

        @Override
        public void run() {
            log.info("i am task[{}], sleep 10s", taskNo);
            Sleeps.seconds(10);
            log.info("task[{}] end", taskNo);
        }
    }

    //拒绝策略
    private class MyPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Task task = (Task) r;
            log.info("===task[{}] rejected!===", task.getTaskNo());
            log.info("active_cn={}, task_cnt={}, completed_task_cnt={}", executor.getActiveCount(), executor.getTaskCount(), executor.getCompletedTaskCount());
            log.info("core_pool_size={}, max_pool_size={}, pool_size={}, largest_pool_size={}", executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getPoolSize(), executor.getLargestPoolSize());
        }
    }

    private void demo() {
        int taskNum = 10;

        int corePoolSize = 2;
        int maxPoolSize = 3;
        int queueSize = 4;
        RejectedExecutionHandler handler = new MyPolicy();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                handler);
        for (int i = 0; i < taskNum; i++) {
            int taskNo = i + 1;
            pool.execute(new Task(String.valueOf(taskNo)));
        }
        pool.shutdown();
        log.info("main thread end");
    }

    public static void main(String[] args) {
        new ThreadPoolExecutor2_Main().demo();
    }
}
