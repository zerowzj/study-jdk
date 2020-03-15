package study.jdk.juc.thread.daemon;

import lombok.extern.slf4j.Slf4j;
import study.jdk.juc.Sleeps;

/**
 * 演示：
 * （1）守护线程依赖创建它的线程，用户线程不依赖
 */
@Slf4j
public class Daemon1_Main {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                log.info("i am daemon thread, sleep 2s");
                Sleeps.seconds(2);
            }
        });
        t.setDaemon(true);
        t.start();

        //线程t sleep 2s执行1次；main线程sleep 10s退出，然后t退出
        log.info("i am thread main, sleep 10s");
        Sleeps.seconds(10);
    }
}
