package study.jdk.juc.thread.notify;

import lombok.extern.slf4j.Slf4j;

/**
 * 等待/通知
 */
@Slf4j
public class WaitNotify1_Main {

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                log.info("A 1");
                //TODO 等待
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                log.info("A 2");
                log.info("A 3");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.info("B 1");
                log.info("B 2");
                log.info("B 3");
                //TODO 通知
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
