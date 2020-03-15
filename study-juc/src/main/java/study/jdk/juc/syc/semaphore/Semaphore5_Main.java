package study.jdk.juc.syc.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 演示：
 */
public class Semaphore5_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Semaphore5_Main.class);

    private static final int T_NUM = 3;

    private static final int PERMITS = 3;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(PERMITS);
        LOGGER.info("初始化：当前有[{}]个许可", semaphore.availablePermits());
        for (int i = 0; i < T_NUM; i++) {
            int no = i;
            pool.submit(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    // 获取许可
                    semaphore.acquire();
                    LOGGER.info("[{}]获取许可({})，剩余：{}", name, no, semaphore.availablePermits());
                    Sleeps.seconds(5);
                    // 访问完后记得释放，否则在控制台只能打印3条记录，之后线程一直阻塞
                    semaphore.release();  //释放许可
                    LOGGER.info("[{}]释放许可({})，剩余：{}", name, no, semaphore.availablePermits());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
        }
        pool.shutdown();
    }
}
