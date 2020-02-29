package study.java.juc.thread.join;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Sleeps;

/**
 * join()用法
 */
public class Join1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Join1_Main.class);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            Sleeps.seconds(2);
            LOGGER.info("i am thread jmap");
        });
        Thread t2 = new Thread(() -> {
            try {
                //阻塞调用线程（主线程或父线程都不准确）
                t1.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            LOGGER.info("i am thread t2");
        });
        t1.start();
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        LOGGER.info("i am thread main");
    }
}
