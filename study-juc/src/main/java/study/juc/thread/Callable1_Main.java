package study.juc.thread;

import lombok.extern.slf4j.Slf4j;
import study.juc.Sleeps;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 演示：返回值的异步执行
 */
@Slf4j
public class Callable1_Main {

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        FutureTask<Integer> task1 = new FutureTask(() -> {
            Sleeps.seconds(3);
            log.info("i am task1");
            return 10;
        });
        FutureTask<Integer> task2 = new FutureTask(() -> {
            Sleeps.seconds(5);
            log.info("i am task2");
            return 21;
        });
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();

        try {
            Integer sum = task1.get() + task2.get();
            log.info("sum= {}, cost_time= {} ms", sum, (System.currentTimeMillis() - now));
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        log.info("main thread end");
    }
}
