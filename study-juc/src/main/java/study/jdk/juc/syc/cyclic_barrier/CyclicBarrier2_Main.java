package study.jdk.juc.syc.cyclic_barrier;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * cyclic: 循环的
 * barrier: 屏障
 * 演示：
 */
@Slf4j
public class CyclicBarrier2_Main {

    public static void main(String[] args) {
        int threadNum = 5;
        int taskNum = 5;
        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        //（★-1）初始化
        CyclicBarrier barrier = new CyclicBarrier(taskNum);
        for (int i = 0; i < taskNum; i++) {
            final int threadNo = i + 1;
            pool.execute(() -> {
                //业务逻辑
                int sec = Randoms.nextInt(10);
                log.info("i am {} thread, sleep {}s",threadNo,  sec);
                Sleeps.seconds(sec);
                log.info("i am {} thread, finish", threadNo);
                //（★-2）
                try {
                    barrier.await();
                } catch (Exception ex) {
                }
                log.info("after await()...");
            });
        }
        log.info("i am main thread");
    }
}
