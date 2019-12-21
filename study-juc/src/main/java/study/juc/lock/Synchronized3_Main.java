package study.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Randoms;
import study.Sleeps;

/**
 * 演示：发生异常JVM会让线程自动释放锁
 */
public class Synchronized3_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Synchronized3_Main.class);

    private synchronized void a() {
        LOGGER.info("i am thread jmap");
        while (true) {
            int random = Randoms.nextInt(10);

            if (random == 6) {
                LOGGER.info("random={}, throw exception", random);
                throw new RuntimeException("sssssss");
            }
            LOGGER.info("random={}, sleep 2s", random);
            Sleeps.seconds(2);
        }
    }

    private synchronized void b() {
        LOGGER.info("i am thread t2");
    }

    private void test() {
        Thread t1 = new Thread(() -> {
            a();
        });
        Thread t2 = new Thread(() -> {
            b();
        });

        //t1获取锁执行，t2阻塞；t1执行发生异常后释放锁，t2获取
        t1.start();
        Sleeps.seconds(1);
        t2.start();
    }

    public static void main(String[] args) {
        new Synchronized3_Main().test();
    }
}
