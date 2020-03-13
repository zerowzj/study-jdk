package study.jdk.juc.synchronized_;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Sleeps;

/**
 * 演示：synchronized访问互斥（资源同步、锁）
 */
@Slf4j
public class Synchronized1Main {

    synchronized void a() {
        log.info("i am a()");
        Sleeps.seconds(10);
    }

    synchronized void b() {
        log.info("i am b()");
    }

    void test() {
        Thread t1 = new Thread(() -> {
            a();
        });
        Thread t2 = new Thread(() -> {
            b();
        });

        t1.start();
        Sleeps.seconds(1);
        t2.start();
    }

    public static void main(String[] args) {
        new Synchronized1Main().test();
    }
}
