package study.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Sleeps;

/**
 * 演示：volatile保证可见性，不保证原子性、顺序性
 */
public class Volatile1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Volatile1_Main.class);

    class Task implements Runnable {

        private boolean running = true;

        int m;

        @Override
        public void run() {
            LOGGER.info("enter into task");
            while (running) {
                //
//                int a = 2;
//                int b = 3;
//                int c = a + b;
//                m = c;
                //输出语句或者sleep时，running是否被volatile修饰，死循环也会停止
                Sleeps.seconds(1);
//                LOGGER.info("i am print");
            }
            LOGGER.info("task is stopped");
        }

        public void stop() {
            LOGGER.info("stop task");
            running = false;
        }
    }

    void test() {
        Task task = new Task();
        Thread t = new Thread(task);
        t.start();

        //jmap，主线程stop
        Sleeps.seconds(1);
        task.stop();
    }

    public static void main(String[] args) {
        new Volatile1_Main().test();
    }
}
