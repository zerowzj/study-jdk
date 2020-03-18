package study.jdk.juc.syc.semaphore;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Sleeps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Semaphore: 信号量
 * 演示：
 */
@Slf4j
public class Semaphore1_Main {

    private static final int T_NUM = 2;

    private static final int PERMITS = 1;

    public static void main(String[] args) {
        //（★-1）初始化
        Semaphore semaphore = new Semaphore(PERMITS);
        List<Thread> tLt = new ArrayList<>();
        for (int i = 0; i < T_NUM; i++) {
            final int index = i;
            Thread t = new Thread(() -> {
                try {
                    //（★-2）获取许可
                    semaphore.acquire();
                    log.info("i am thread[{}]", index);
                    Sleeps.seconds(3);
                    log.info("i am thread[{}]", index);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    //（★-3）
                    semaphore.release();
                }
            });
            tLt.add(t);
        }
        for (Thread t : tLt) {
            t.start();
        }
    }
}
