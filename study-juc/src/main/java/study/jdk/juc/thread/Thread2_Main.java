package study.jdk.juc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示：
 */
public class Thread2_Main {

    public static final Logger LOGGER = LoggerFactory.getLogger(Thread2_Main.class);

    class Task implements Runnable {

        private int no;

        @Override
        public void run() {
            for (int i = 0; i < 6; i++) {
                synchronized (this) {
                    LOGGER.info("当前号：{}", ++no);
                }
            }
        }
    }

    void test() {
        Task task = new Task();
        new Thread(task, "一号窗口").start();
        new Thread(task, "二号窗口").start();
        new Thread(task, "三号窗口").start();
    }

    public static void main(String[] args) {
        new Thread2_Main().test();
    }
}
