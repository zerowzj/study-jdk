package study.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Sleeps;

import java.util.concurrent.locks.LockSupport;

/**
 * 演示：
 */
public class LockSupport2_Main {

    public static final Logger LOGGER = LoggerFactory.getLogger(LockSupport2_Main.class);

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            LOGGER.info("i am t thread, begin park");
            LockSupport.park();
            LOGGER.info("i am t thread, begin unpark");
        });
        t.start();

        Sleeps.seconds(5);
        LOGGER.info("main thread begin unpark");

         //线程t获取许可证，同时唤醒阻塞
        LockSupport.unpark(t);
    }
}
