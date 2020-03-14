package study.jdk.juc.pool.scheduled_thread_pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 演示：调度线程池
 * （1）多个线程多个任务，任务可以并发执行
 */
public class ScheduledThreadPool1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledThreadPool1_Main.class);

    private static final int THREAD_NUM = 5;

    private static final int TASK_NUM = 5;

    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(THREAD_NUM);
        long delay = 0;
        for (int i = 0; i < TASK_NUM; i++) {
            int taskNo = i + 1;
            schedule.schedule(() -> {
                        LOGGER.info("i am task[{}], sleep 3s", taskNo);
                        Sleeps.seconds(3);
                        LOGGER.info("task[{}] end", taskNo);
                    },
                    delay, TimeUnit.SECONDS);
        }
    }
}
