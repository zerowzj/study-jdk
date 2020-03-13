package study.jdk.juc.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示：
 * （1）线程有共享对象
 */
public class FixedThreadPool2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedThreadPool2_Main.class);

    private static final int WINDOW_NUM = 3;

    /**
     * 叫号机
     */
    private class Machine {

        private int no;

        public int getNo() {
            return ++no;
        }
    }

    /**
     * 窗口，一个窗口一个线程，窗口是业务执行单元，线程是并发单元
     */
    private class Window implements Runnable {

        private String winNo;

        private Machine machine;

        public Window(String winNo, Machine machine) {
            this.winNo = winNo;
            this.machine = machine;
        }

        @Override
        public void run() {
            while (true) {
                int no;
                //同步
                synchronized (machine) {
                    no = machine.getNo();
                }
                //临界区
//                no = machine.getNo();
                LOGGER.info("请 {} 号到[{}号窗口]", no, winNo);
                Sleeps.seconds(3);
            }
        }
    }

    private void test() {
        ExecutorService pool = Executors.newFixedThreadPool(WINDOW_NUM);
        Machine machine = new Machine();
        for (int i = 0; i < WINDOW_NUM; i++) {
            pool.execute(new Window(String.valueOf(i + 1), machine));
        }
        pool.shutdown();
    }

    public static void main(String[] args) {
        new FixedThreadPool2_Main().test();
    }
}
