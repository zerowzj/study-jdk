package study.jdk.juc.thread.notify;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

/**
 * 一个线程计算
 */
@Slf4j
public class WaitNotify2_Main {

    private int sum = 0;

    public void test() {
        Object lock = new Object();
        //（★）计算线程
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                for (; ; ) {
                    sum += 100;
                    Sleeps.seconds(1);
                    log.info("==>{}", sum);
                    if (sum == 500) {
                        log.info("jmap notify start");
                        lock.notify(); //仅仅
                        log.info("jmap notify end");
                        //去掉break，notify只通知t2但不释放锁，t2无法再wait()方法处返回
                        //保证跳出for循环，执行notify()，不会跳出循环
                        break;
                    }
                }
            }
        });
        //（★）输出线程
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.info("t2 wait start");
                    lock.wait();
                    log.info("t2 wait end");
                    log.info("t2 sum= {}", sum);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //（★）t2 需要先执行并且紧跟着要sleep一下保证其充分启动
        t2.start();
        Sleeps.seconds(1);
        t1.start();
    }

    public static void main(String[] args) {
        new WaitNotify2_Main().test();
    }
}
