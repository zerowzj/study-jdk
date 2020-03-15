package study.jdk.juc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

/**
 * 演示：线程执行过程中异常未被捕获，线程停止运行；
 * 如果线程持有某个对象的监视器锁，会被立即释放
 */
public class Exception1_Main {

    public static final Logger LOGGER = LoggerFactory.getLogger(Exception1_Main.class);

    void a() {
        for (; ; ) {
            int random = Randoms.nextInt(10);
            if (random == 0) {
                throw new RuntimeException("random is 0");
            }
            LOGGER.info("random is {}, sleep 2s", random);
            Sleeps.seconds(2);
        }
    }

    void test() {
        Thread t = new Thread(() -> {
            //不捕获异常，不执行for
            a();
            //捕获异常，执行for
//            try {
//                a();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                LOGGER.info("thread t exception");
//            }
            
            for (; ; ) {
                int a = 1;
                int b = a + 2;
                int c = a + b;
            }
        });
        t.start();

        while (true) {
            LOGGER.info("thread t state is {}", t.getState().name());
            Sleeps.seconds(3);
        }
    }

    public static void main(String[] args) {
        new Exception1_Main().test();
    }
}
