package study.jdk.juc.thread.callable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 演示：返回值的异步执行
 */
@Slf4j
public class Callable2_Main {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = exec.submit(task);
        exec.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("主线程在执行任务");
        try {
            System.out.println("task运行结果" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("所有任务执行完毕");
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 1; i <= 100; i++)
                sum += i;
            return sum;
        }
    }
}
