package study.jdk.juc.thread.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.jdk.juc.Sleeps;

import java.io.File;
import java.util.Scanner;

/**
 * 这里新建了一个磁盘文件扫描的任务，扫描某个目录下的所有文件并将文件路径打印到控制台，扫描的过程可能会很长。
 * 若需要中止该任务，只需在控制台键入quit并回车即可
 */
public class Interrupt5_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interrupt5_Main.class);

    Thread t;

    static void listFile(File file) throws InterruptedException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        if (file.isFile() && !file.isHidden()) {
            LOGGER.info("file: {}", file.getName());
            return;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException("文件扫描任务被中断");
        }
        LOGGER.info("===>{}", file.getName());
        File[] files = file.listFiles();
        for (File f : files) {
            LOGGER.info("sleep 2s");
            Sleeps.seconds(2);
            listFile(f);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                listFile(new File("D:\\build"));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
//        Scanner scan = new Scanner(System.in);
//        String read = scan.nextLine();
        Thread t2 = new Thread(() -> {
            while (true) {
                Scanner scan = new Scanner(System.in);
                String read = scan.nextLine();
                if ("quit".equalsIgnoreCase(read)) {
                    if (t1.isAlive()) {
                        t1.interrupt();
                        return;
                    }
                } else {
                    System.out.println("输入quit退出文件扫描");
                }
            }
        });
        t2.start();
        t1.start();
    }
}
