package study.jdk.juc.pool.single_thread_executor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示：
 * （1）单线程执行器
 * （2）主线程结束，线程池线程可能还在运行
 * （3）线程和任务是2个概念，数量关系为M:N（线程数=任务数，线程数>任务数，线程数<任务数）
 * （4）线程池是个重量级的对象，尽量全局共享，使用完关闭
 */
@Slf4j
public class SingleThreadExecutor_Main {

    private static final int TASK_NUM = 5;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < TASK_NUM; i++) {
            int taskNo = i + 1;
            pool.execute(() -> {
                log.info("i am task[{}], sleep 2s", taskNo);
                Sleeps.seconds(2);
            });
        }
        log.info("i am main thread");
        pool.shutdown();
        log.info("main thread end");
        Sleeps.seconds(20);
        pool.execute(()-> {
            log.info("dsfasd");
        });
    }
}
