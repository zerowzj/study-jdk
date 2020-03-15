package study.jdk.juc.thread.notify;

import lombok.extern.slf4j.Slf4j;

/**
 * 等待/通知
 * 如果需要获取执行结果，就必须通过共享变量或者使用线程通信的方式来达到效果
 * 继承Thread和实现Runnable接口方式都有一个缺陷就是：在执行完任务之后无法获取执行结果
 * 如果需要获取执行结果，就必须通过共享变量或者使用线程通信的方式来达到效果
 */
@Slf4j
public class WaitNotify1_Main {

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                log.info("A 1");
                //（★）等待
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
                //（★）通知
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
