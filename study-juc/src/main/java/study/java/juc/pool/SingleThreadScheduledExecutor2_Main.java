package study.java.juc.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 演示：
 * （1）一次性任务
 * （2）可获取返回值
 */
public class SingleThreadScheduledExecutor2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadScheduledExecutor2_Main.class);

    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        LOGGER.info("schedule start");
        long delay = 5;
        ScheduledFuture<String> future = schedule.schedule(() -> {
                    String result = "i am return value";
                    return result;
                },
                delay,
                TimeUnit.SECONDS);
        //阻塞调用线程，获取返回值
        try {
            String data = future.get();
            LOGGER.info("data={}", data);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        LOGGER.info("schedule shutdown");
        schedule.shutdown();
        LOGGER.info("main thread end");
    }
}
