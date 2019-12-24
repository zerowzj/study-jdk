package study.juc.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 演示：线程池执行有返回值的任务
 * （1）任务独立，无临界区
 * （2）Future.get() 阻塞当前线程；List<Future>时，列表后可能比列表前先执行完成
 */
public class FixedThreadPool3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedThreadPool3_Main.class);

    private static final int THREAD_NUM = 10;

    private static final int TASK_NUM = 10;

    /**
     * 任务
     */
    private class Task implements Callable<Result> {

        private String taskNo;

        private int value;

        public Task(String taskNo, int value) {
            this.taskNo = taskNo;
            this.value = value;
        }

        @Override
        public Result call() throws Exception {
            LOGGER.info("task_no={}, sleep {}s", taskNo, value);
            Sleeps.seconds(value);
            Result result = new Result(taskNo, value * 1000);
            LOGGER.info("task_no={} finish", taskNo);
            return result;
        }
    }

    /**
     * 任务结果
     */
    private class Result implements Serializable {

        private String taskNo;

        private int value;

        public Result(String taskNo, int value) {
            this.taskNo = taskNo;
            this.value = value;
        }

        public String getTaskNo() {
            return taskNo;
        }

        public int getValue() {
            return value;
        }
    }

    private void test() {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
        long start = System.currentTimeMillis();
        //任务异步执行
        List<Future<Result>> futureLt = new ArrayList<>();
        int j = TASK_NUM;
        for (int i = 0; i < TASK_NUM; i++) {
            String taskNo = String.valueOf(i + 1);
            int value = j--;
            futureLt.add(pool.submit(new Task(taskNo, value)));
        }
        //阻塞主线程获取结果
        for (Future<Result> future : futureLt) {
            try {
                Result result = future.get();
                LOGGER.info("task_no={}, begin_time={}, end_time={}", result.taskNo, result.getValue());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
        LOGGER.info("cost_time={} ms", System.currentTimeMillis() - start);
        pool.shutdown();
    }

    public static void main(String[] args) {
        new FixedThreadPool3_Main().test();
    }
}
