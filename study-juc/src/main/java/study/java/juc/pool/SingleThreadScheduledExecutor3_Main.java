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
 * （1）Fixed-Delay，固定延时间隔执行
 * （2）周期性任务
 * （3）以上一个任务结束时开始计时，delay过去后，立即执行
 * （4）某个任务发生异常，如忽略异常，则该任务执行被终止，如捕获异常，仅任务的本次执行被终止；异常不影响其他任务执行，
 */
public class SingleThreadScheduledExecutor3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadScheduledExecutor3_Main.class);

    private static final int TASK_NUM = 2;

    private void doTask(String taskNo, long time) {
        LOGGER.info("i am task[{}]", taskNo);
        if ("2".equals(taskNo)) {
            throw new RuntimeException("task no is 2");
        }
        Sleeps.seconds(time);
        LOGGER.info("task[{}] end", taskNo);
    }

    private void test() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        LOGGER.info("schedule start");
        long initialDelay = 0;
        long delay = 5;
        for (int i = 0; i < TASK_NUM; i++) {
            int taskNo = i + 1;
            long time = Randoms.nextLong(9);
            schedule.scheduleWithFixedDelay(() -> {
                        //忽略异常
//                        doTask(String.valueOf(taskNo), time);
                        //捕获异常
                        try {
                            doTask(String.valueOf(taskNo), time);
                        } catch (Exception ex) {
                            LOGGER.info("", ex);
                        }
                    },
                    initialDelay,
                    delay,
                    TimeUnit.SECONDS);
        }
        LOGGER.info("main thread end");
    }

    public static void main(String[] args) {
        new SingleThreadScheduledExecutor3_Main().test();
    }
}
