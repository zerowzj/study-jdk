package study.jdk.juc.volatile_;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示：volatile实现轻量级同步
 */
@Slf4j
public class Volatile2Main {

    private static int TIME = 5;

    //    private int count;
    private volatile int count;

    private class Counter implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < TIME; i++) {
                log.info("当前计数：{}", ++count);
            }
        }
    }

    private void test() {
        new Thread(new Counter()).start();
        new Thread(new Counter()).start();
        new Thread(new Counter()).start();
    }

    public static void main(String[] args) {
        new Volatile2Main().test();
    }
}
