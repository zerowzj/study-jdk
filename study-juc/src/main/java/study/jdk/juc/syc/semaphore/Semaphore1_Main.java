package study.jdk.juc.syc.semaphore;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        //permits: 初始化可用的许可数目。
        //fair: 若该信号量保证在征用时按FIFO的顺序授予许可，则为true，否则为false；
        Semaphore semaphore = new Semaphore(PERMITS);
        List<Thread> tLt = new ArrayList<>();
        for (int i = 0; i < T_NUM; i++) {
            final int index = i;
            Thread t = new Thread(() -> {
                try {
                    //获取1个许可
                    semaphore.acquire();
                    log.info("i am thread[{}]", index);
                    Sleeps.seconds(3);
                    log.info("i am thread[{}]", index);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
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
