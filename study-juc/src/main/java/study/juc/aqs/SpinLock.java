package study.juc.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.juc.Sleeps;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpinLock.class);

    private AtomicReference<Thread> cas = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
       /* boolean lock = cas.compareAndSet(null, current);
        while (!lock) */
        //如果没获取到锁，则通过CAS自旋；使用上面两行代码为什么不行？
        while (!cas.compareAndSet(null, current)) {
            LOGGER.info("thread[{}] is spinning", current.getName());
            Sleeps.seconds(1);
        }
        LOGGER.info("thread[{}] get the lock", current.getName());
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        boolean unlock = cas.compareAndSet(current, null);
        LOGGER.info("thread[{}] release the lock[{}]", current.getName(), unlock);
    }
}
