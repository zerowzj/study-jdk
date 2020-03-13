package study.jdk.juc.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 可重入自旋锁
 */
public class ReentrantSpinLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantSpinLock.class);

    private AtomicReference<Thread> cas = new AtomicReference<>();

    private int count;

    public void lock() {
        Thread current = Thread.currentThread();
        if (current == cas.get()) {
            count++;
            return;
        }

        while (!cas.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == cas.get()) {
            if (count > 0) { //如果大于0，表示当前线程多次获取了该锁，释放锁通过count减一来模拟
                count--;
            } else { //如果count==0，可以将锁释放，这样就能保证获取锁的次数与释放锁的次数是一致的了
                cas.compareAndSet(current, null);
            }
        }
    }
}
