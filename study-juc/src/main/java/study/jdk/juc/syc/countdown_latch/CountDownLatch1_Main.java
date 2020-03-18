package study.jdk.juc.syc.countdown_latch;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

import java.util.concurrent.CountDownLatch;

/**
 * countdown: 倒数计秒
 * latch: 闭锁
 * 演示：CountDownLatch基本用法
 */
@Slf4j
public class CountDownLatch1_Main {

    public static void main(String[] args) {
        //（★）
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            //业务逻辑
            int sec = Randoms.nextInt(10);
            log.info("i am t1 thread, sleep {}s", sec);
            Sleeps.seconds(sec);
            log.info("i am t1 thread, finish");
            //（★）
            latch.countDown();
        });
        Thread t2 = new Thread(() -> {
            int sec = Randoms.nextInt(10);
            log.info("i am t2 thread, sleep {}s", sec);
            Sleeps.seconds(sec);
            log.info("i am t2 thread, finish");
            //（★）
            latch.countDown();
        });
        t1.start();
        t2.start();

        try {
            //（★）集合点
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        log.info("i am main thread");
    }
}
