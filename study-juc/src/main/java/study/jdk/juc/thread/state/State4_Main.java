package study.jdk.juc.thread.state;

import study.jdk.juc.Sleeps;

/**
 * 演示：线程状态BLOCKED
 */
public class State4_Main {

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                Sleeps.deep();
            }
        }, "jmap");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
            }
        }, "t2");
        //t1持有锁，t2阻塞于该锁
        t1.start();
        t2.start();
    }
}
/*
 * "t2" #12 prio=5 os_prio=0 tid=0x000000001b9ef000 nid=0x43a0 waiting for monitor entry [0x000000001c3cf000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *         at study.jdk.juc.thread.State4_Main.lambda$main$1(State4_Main.java:19)
 *         - waiting to lock <0x00000000d7149500> (a java.lang.Object)
 *         at study.jdk.juc.thread.State4_Main$$Lambda$2/381259350.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "jmap" #11 prio=5 os_prio=0 tid=0x000000001b9ec000 nid=0x3be0 waiting on condition [0x000000001c2ce000]
 *    java.lang.Thread.State: TIMED_WAITING (sleeping)
 *         at java.lang.Thread.sleep(Native Method)
 *         at java.lang.Thread.sleep(Thread.java:340)
 *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 *         at study.juc.Sleeps.seconds(Sleeps.java:9)
 *         at study.jdk.juc.thread.State4_Main.lambda$main$0(State4_Main.java:14)
 *         - locked <0x00000000d7149500> (a java.lang.Object)
 *         at study.jdk.juc.thread.State4_Main$$Lambda$1/1607521710.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:745)
 */
