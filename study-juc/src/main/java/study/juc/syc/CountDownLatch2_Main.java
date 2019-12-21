package study.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Sleeps;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CountDownLatch;

/**
 * 演示：
 */
public class CountDownLatch2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        CountDownLatch countDown = new CountDownLatch(1);
        CountDownLatch await = new CountDownLatch(5);

        for (int i = 0; i < 5; ++i) {
            new Thread(new Task(countDown, await)).start();
        }
        LOGGER.info("用于触发处于等待状态的线程开始工作");
        LOGGER.info("用于触发处于等待状态的线程工作完成，等待状态线程开始工作");
        Sleeps.seconds(4);
        countDown.countDown();
        try {
            await.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        LOGGER.info("Bingo!");
    }

    private static class Task implements Runnable {

        private final CountDownLatch countDown;

        private final CountDownLatch await;

        public Task(CountDownLatch countDown, CountDownLatch await) {
            this.countDown = countDown;
            this.await = await;
        }

        @Override
        public void run() {
            try {
                //TODO 线程启动后需等待countDown.countDown()执行后运行下面代码
                countDown.await();// 等待主线程执行完毕，获得开始执行信号

                LOGGER.info("处于等待的线程开始自己预期工作");
                await.countDown(); //完成预期工作，发出完成信号
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
