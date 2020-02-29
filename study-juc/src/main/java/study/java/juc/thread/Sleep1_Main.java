package study.java.juc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.java.juc.Sleeps;

/**
 * 演示：wait()与sleep()区别，两者都释放cpu；前者释放锁，后者不释放锁
 */
public class Sleep1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sleep1_Main.class);

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("i am thread jmap");
                //不释放锁，t2无法获取锁阻塞于该锁
                Sleeps.seconds(5);
//                try {
//                    //释放锁，t2获取锁并执行
//                    lock.wait();
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("i am thread t2");
            }
        });

        t1.start();
        Sleeps.seconds(1);
        t2.start();
    }
}
