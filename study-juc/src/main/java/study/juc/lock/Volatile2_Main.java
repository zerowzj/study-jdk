package study.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示：volatile实现轻量级同步
 */
public class Volatile2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Volatile2_Main.class);

    private static int TIME = 5;

    //    private int count;
    private volatile int count;

    private class Counter implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < TIME; i++) {
                LOGGER.info("当前计数：{}", ++count);
            }
        }
    }

    private void test() {
        new Thread(new Counter()).start();
        new Thread(new Counter()).start();
        new Thread(new Counter()).start();
    }

    public static void main(String[] args) {
        new Volatile2_Main().test();
    }
}
