package study.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Semaphore: 信号量
 * 演示：
 */
public class Semaphore1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Semaphore1_Main.class);

    private static final int T_NUM = 2;

    private static final int PERMITS = 1;

    public static void main(String[] args) {
        //permits: 初始化可用的许可数目。
        //   fair: 若该信号量保证在征用时按FIFO的顺序授予许可，则为true，否则为false；
        Semaphore semaphore = new Semaphore(PERMITS);
        List<Thread> tLt = new ArrayList<>();
        for (int i = 0; i < T_NUM; i++) {
            final int index = i;
            Thread t = new Thread(() -> {
                try {
                    semaphore.acquire();
                    LOGGER.info("i am thread[{}]", index);
                    Sleeps.seconds(3);
                    LOGGER.info("i am thread[{}]", index);
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
