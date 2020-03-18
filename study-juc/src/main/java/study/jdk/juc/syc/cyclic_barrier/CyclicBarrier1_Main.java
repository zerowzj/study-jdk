package study.jdk.juc.syc.cyclic_barrier;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * cyclic: 循环的
 * barrier: 屏障
 * 演示：
 */
@Slf4j
public class CyclicBarrier1_Main {

    public static void main(String[] args) {
        //（★-1）初始化
        CyclicBarrier barrier = new CyclicBarrier(2);
        Thread t1 = new Thread(() -> {
            try {
                //业务逻辑
                int sec = Randoms.nextInt(10);
                log.info("i am t1 thread, sleep {}s", sec);
                Sleeps.seconds(sec);
                log.info("i am t1 thread, finish");
                //（★-2）
                barrier.await();
                log.info("i am t1 thread");
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
        });
        Thread t2 = new Thread(() -> {
            try {
                int sec = Randoms.nextInt(10);
                log.info("i am t2 thread, sleep {}s", sec);
                Sleeps.seconds(sec);
                log.info("i am t2 thread, finish");

                barrier.await();
                log.info("i am t2 thread");
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
        });
        t1.start();
        t2.start();
        log.info("i am main thread");
    }
}
