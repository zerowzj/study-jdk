package study.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Sleeps;

/**
 * （1）线程对象作为锁
 * synchronized获得对象锁是线程的实例时，此时比较特殊，
 * 当该线程终止的时候，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线程。
 */
public class ThreadAsLock1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadAsLock1_Main.class);

    public static void main(String[] args) {
        Thread lock = new Thread(() -> {
            LOGGER.info("i am lock thread, sleep 10s");
            Sleeps.seconds(10);
        });
        lock.start();

        synchronized (lock) {
            try {
                lock.wait(15 * 1000);
                LOGGER.info("i am main thread");
            } catch (InterruptedException ex) {
            }
        }
    }
}
