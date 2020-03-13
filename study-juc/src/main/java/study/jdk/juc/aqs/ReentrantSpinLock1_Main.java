package study.jdk.juc.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示：
 * （1）锁可重入
 */
public class ReentrantSpinLock1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantSpinLock1_Main.class);

    private ReentrantSpinLock lock = new ReentrantSpinLock();

    private void a() {
        lock.lock();
        try {
            LOGGER.info("i am a method");
            b();
        } finally {
            lock.unlock();
        }
    }

    private void b() {
        lock.lock();
        try {
            LOGGER.info("i am b method");
        } finally {
            lock.unlock();
        }
    }

    private void test() {
        Thread t = new Thread(() -> {
            a();
        });
        t.start();
        LOGGER.info("main thread end");
    }

    public static void main(String[] args) {
        new ReentrantSpinLock1_Main().test();
    }
}
