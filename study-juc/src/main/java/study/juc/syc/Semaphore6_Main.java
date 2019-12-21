package study.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Sleeps;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 演示：现在有一个三个信号灯，启动10个线程分别获取信号灯，当信号灯被占用时，其他线程只能等待，当信号灯被释放则等待线程获取信号灯。
 */
public class Semaphore6_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Semaphore6_Main.class);

    private static final int T_NUM = 10;

    private static final int PERMITS = 3;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(PERMITS, true);
        for (int i = 0; i < T_NUM; i++) {
            pool.execute(() -> {
                String name = Thread.currentThread().getName();
                try {
                    //获取信号灯许可
                    semaphore.acquire();
                    LOGGER.info("Thread[{}]进入，当前系统的并发数是：{}", name, PERMITS - semaphore.availablePermits());
                    Sleeps.seconds(new Random().nextInt(10));
                    LOGGER.info("Thread[{}]即将离开", name);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();//释放信号灯
                    LOGGER.info("Thread[{}]已经离开，当前系统的并发数是：{}", name, PERMITS - semaphore.availablePermits());
                }
            });
        }
        pool.shutdown();
    }
}
