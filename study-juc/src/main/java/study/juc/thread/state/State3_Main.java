package study.juc.thread.state;

/**
 *
 */
public class State3_Main {

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t = new Thread(() -> {
            synchronized (lock) {
                try {
//                    lock.wait();
                    lock.wait(Integer.MAX_VALUE);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }, "jmap");
        t.start();
    }
}
/*
 * "jmap" #11 prio=5 os_prio=0 tid=0x000000001c03e800 nid=0x2fb0 in Object.wait() [0x000000001c91e000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000d71493b0> (a java.lang.Object)
 *         at java.lang.Object.wait(Object.java:502)
 *         at study.jdk.juc.thread.State3_Main.lambda$main$0(State3_Main.java:13)
 *         - locked <0x00000000d71493b0> (a java.lang.Object)
 *         at study.jdk.juc.thread.State3_Main$$Lambda$1/1607521710.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "jmap" #11 prio=5 os_prio=0 tid=0x000000001b86d000 nid=0x2344 in Object.wait() [0x000000001c13f000]
 *    java.lang.Thread.State: TIMED_WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000d7149338> (a java.lang.Object)
 *         at study.jdk.juc.thread.State3_Main.lambda$main$0(State3_Main.java:24)
 *         - locked <0x00000000d7149338> (a java.lang.Object)
 *         at study.jdk.juc.thread.State3_Main$$Lambda$1/1607521710.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:745)
 */
