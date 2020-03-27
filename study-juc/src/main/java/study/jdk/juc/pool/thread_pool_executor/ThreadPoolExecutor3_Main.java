package study.jdk.juc.pool.thread_pool_executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示
 * （1）
 * （2）
 */
@Slf4j
public class ThreadPoolExecutor3_Main {

    //任务
    private class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            return null;
        }
    }

    private void demo() {
        int corePoolSize = 2;
        int maxPoolSize = 4;
        int queueSize = 2;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize));

        int taskSize = 5;
        for (int i = 0; i < taskSize; i++) {
            pool.execute(() -> {
                try {
                    int sec = 5;
                    log.info("sleep {} s", sec);
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {

                }
            });
        }
    }

    public static void main(String[] args) {
        new ThreadPoolExecutor3_Main().demo();
    }
}
