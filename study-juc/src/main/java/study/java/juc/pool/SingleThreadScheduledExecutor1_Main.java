package study.java.juc.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Randoms;
import study.java.juc.Sleeps;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 演示：
 * （1）单线程调度执行器
 * （3）一次性任务
 * （4）某个任务执行异常不影响其他任务执行
 */
public class SingleThreadScheduledExecutor1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadScheduledExecutor1_Main.class);

    private static final int TASK_NUM = 5;

    private void doTask(String taskNo, long delay) {
        if ("2".equals(taskNo)) {
            throw new RuntimeException("task no is 2");
        }
        LOGGER.info("i am task[{}], delay {}s", taskNo, delay);
        Sleeps.seconds(delay);
        LOGGER.info("task[{}] end", taskNo);
    }

    private void test() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        LOGGER.info("schedule start");
        for (int i = 0; i < TASK_NUM; i++) {
            int taskNo = i + 1;
            long delay = Randoms.nextLong(10);
            schedule.schedule(() -> {
                        //忽略异常
                        //doTask(String.valueOf(taskNo), delay);
                        //捕获异常
                        try {
                            doTask(String.valueOf(taskNo), delay);
                        } catch (Exception ex) {
                            LOGGER.info("", ex);
                        }
                    },
                    delay,
                    TimeUnit.SECONDS);
        }
        LOGGER.info("schedule shutdown");
        schedule.shutdown();
        LOGGER.info("main thread end");
    }

    public static void main(String[] args) {
        new SingleThreadScheduledExecutor1_Main().test();
    }
}
