package study.jdk.juc.syc.count_latch;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CountDownLatch;

/**
 * countdown: 倒数计秒
 * latch: 闭锁
 * 演示：CountDownLatch基本用法
 */
@Slf4j
public class CountDownLatch1_Main {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            Sleeps.seconds(2);
            log.info("i am t1 thread");
            latch.countDown();
        });
        Thread t2 = new Thread(() -> {
            Sleeps.seconds(2);
            log.info("i am t2 thread");
            latch.countDown();
        });
        t1.start();
        t2.start();

        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        log.info("i am main thread");
    }
}
