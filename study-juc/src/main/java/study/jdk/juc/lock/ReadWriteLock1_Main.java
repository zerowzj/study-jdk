package study.jdk.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示：
 */
public class ReadWriteLock1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteLock1_Main.class);

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
        });

    }
}
