package study.jdk.juc.thread.join;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Join2_Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Join2_Main.class);

    class MyThread extends Thread {
        private Thread t;

        MyThread(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                t.join();
                LOGGER.info("t3");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void test() {
        Thread t1 = new Thread(() -> {
            LOGGER.info("jmap");
        });
        Thread t2 = new Thread(() -> {
            LOGGER.info("t2");
        });
        Thread t3 = new MyThread(t1);

        t3.start();
        t2.start();
        t1.start();
    }

    public static void main(String[] args) {
        new Join2_Main().test();
    }
}
