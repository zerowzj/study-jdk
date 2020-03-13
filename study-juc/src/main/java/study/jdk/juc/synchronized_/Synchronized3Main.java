package study.jdk.juc.synchronized_;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Randoms;
import study.jdk.juc.Sleeps;

/**
 * 演示：发生异常JVM会让线程自动释放锁
 */
@Slf4j
public class Synchronized3Main {

    private synchronized void a() {
        log.info("i am thread jmap");
        while (true) {
            int random = Randoms.nextInt(10);

            if (random == 6) {
                log.info("random={}, throw exception", random);
                throw new RuntimeException("sssssss");
            }
            log.info("random={}, sleep 2s", random);
            Sleeps.seconds(2);
        }
    }

    private synchronized void b() {
        log.info("i am thread t2");
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
        new Synchronized3Main().test();
    }
}
