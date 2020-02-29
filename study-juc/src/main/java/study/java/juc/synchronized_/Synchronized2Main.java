package study.java.juc.synchronized_;

import lombok.extern.slf4j.Slf4j;
import study.java.juc.Sleeps;

/**
 * 演示：synchronized不可中断
 */
@Slf4j
public class Synchronized2Main {

    private Object lock = new Object();

    void test() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                log.info("i am thread jmap");
                Sleeps.seconds(20);
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.info("i am thread t2");
            }
        });
        t1.start();
        Sleeps.seconds(1);
        t2.start();

        //t1持有锁，t2阻塞于该锁，main中断t2
        Sleeps.seconds(3);
        t2.interrupt();
    }

    public static void main(String[] args) {
        new Synchronized2Main().test();
    }
}
