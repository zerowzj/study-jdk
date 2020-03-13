package study.jdk.juc.thread.state;

import study.jdk.juc.Sleeps;

/**
 * 演示：
 * （1）线程状态TIMED_WAITING和WAITING
 * jps jstack
 */
public class State2_Main {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            Sleeps.seconds(Integer.MAX_VALUE);
        }, "jmap");
        t.start();
        Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (Exception ex) {

            }
        }
    }
}
/*
 * "jmap" #11 prio=5 os_prio=0 tid=0x000000001b57e800 nid=0x6f0 waiting on condition [0x000000001be3f000]
 *    java.lang.Thread.State: TIMED_WAITING (sleeping)
 *         at java.lang.Thread.sleep(Native Method)
 *         at java.lang.Thread.sleep(Thread.java:340)
 *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 *         at study.juc.Sleeps.seconds(Sleeps.java:9)
 *         at study.jdk.juc.thread.State2_Main.lambda$main$0(State2_Main.java:13)
 *         at study.jdk.juc.thread.State2_Main$$Lambda$1/764977973.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "main" #1 prio=5 os_prio=0 tid=0x0000000004ba2800 nid=0xf38 in Object.wait() [0x00000000049ee000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000d7149628> (a java.lang.Thread)
 *         at java.lang.Thread.join(Thread.java:1249)
 *         - locked <0x00000000d7149628> (a java.lang.Thread)
 *         at java.lang.Thread.join(Thread.java:1323)
 *         at study.jdk.juc.thread.State2_Main.main(State2_Main.java:18)
 *
 * "main" #1 prio=5 os_prio=0 tid=0x0000000005012800 nid=0x20b4 in Object.wait() [0x0000000004f0f000]
 *    java.lang.Thread.State: TIMED_WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000d7149238> (a java.lang.Thread)
 *         at java.lang.Thread.join(Thread.java:1257)
 *         - locked <0x00000000d7149238> (a java.lang.Thread)
 *         at study.jdk.juc.thread.State2_Main.main(State2_Main.java:37)
 */
