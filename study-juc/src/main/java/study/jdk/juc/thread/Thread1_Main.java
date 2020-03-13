package study.jdk.juc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

/**
 * 演示：多任务（异步、并发）
 */
public class Thread1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread1_Main.class);

    private void enjoy_music() {
        for (; ; ) {
            LOGGER.info("enjoy music...");
            Sleeps.seconds(1);
        }
    }

    private void enjoy_news() {
        for (; ; ) {
            LOGGER.info("enjoy news...");
            Sleeps.seconds(3);
        }
    }

    public void test() {
        //
        //enjoy_music();
        //
        Thread t = new Thread(() -> {
            enjoy_music();
        });
        t.start();

        enjoy_news();
    }

    public static void main(String[] args) {
        new Thread1_Main().test();
    }
}
