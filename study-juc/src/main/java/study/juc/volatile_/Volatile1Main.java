package study.juc.volatile_;

import lombok.extern.slf4j.Slf4j;
import study.juc.Sleeps;

/**
 * 演示：volatile保证可见性，不保证原子性、顺序性
 */
@Slf4j
public class Volatile1Main {

    class Task implements Runnable {

        private boolean running = true;

        @Override
        public void run() {
            log.info("enter into task");
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
            log.info("task is stopped");
        }

        public void stop() {
            log.info("stop task");
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
        new Volatile1Main().test();
    }
}
