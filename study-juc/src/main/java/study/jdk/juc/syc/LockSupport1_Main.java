package study.jdk.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * 演示：
 */
public class LockSupport1_Main {

    public static final Logger LOGGER = LoggerFactory.getLogger(LockSupport1_Main.class);

    public static void main(String[] args) {
        LOGGER.info("begin park");

        LockSupport.unpark(Thread.currentThread());
        //main线程获取许可时，立即返回，否则阻塞main线程（默认情况下调用线程是不持有许可证的）
        LockSupport.park();

        LOGGER.info("end park");
    }
}
