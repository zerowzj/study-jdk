
package study.jdk.juc.thread.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示：
 * （1）线程状态NEW和RUNNABLE
 */
public class State1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(State1_Main.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            LOGGER.info("i am t thread");
        });
        LOGGER.info("after new(), state= {}", t.getState().name());

        t.start();
        LOGGER.info("after start(), state= {}", t.getState().name());
    }
}
