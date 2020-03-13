package study.jdk.juc.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示：
 * （1）
 */
public class AQS1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(AQS1_Main.class);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Mutex lock = new Mutex();
        for (int i = 0; i < 3; i++) {
            int taskNo = i + 1;
            pool.execute(() -> {
//                lock.lock();
                try {
                    LOGGER.info("task[{}] start", taskNo);
                    Sleeps.seconds(5);
                    LOGGER.info("task[{}] end", taskNo);
                } finally {
//                    lock.unlock();
                }
            });
        }
        pool.shutdown();
        LOGGER.info("main thread end");
    }
}
