package study.jdk.juc.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 演示：
 */
public class ScheduledThreadPool2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledThreadPool2_Main.class);

    private static final int THREAD_NUM = 3;

    private static final int TASK_NUM = 2;

    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(THREAD_NUM);
        for (int i = 0; i < TASK_NUM; i++) {

        }
        schedule.shutdown();
        LOGGER.info("schedule shutdown");
    }
}
