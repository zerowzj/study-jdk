package study.jdk.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

/**
 * 演示：N个人去银行存款，但是该银行只有两个办公柜台，有空位则上去存钱，没有空位则只能去排队等待
 */
public class Semaphore2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Semaphore2_Main.class);

    private static final int N = 100;

    private static final int PERMITS = 100;

    /**
     * 银行类
     */
    class Bank {
        private int account = 0;

        public int getAccount() {
            return this.account;
        }

        public void save(int money) {
            this.account += money;
        }
    }

    /**
     * 存钱类
     */
    class SaveMoney implements Runnable {

        private Bank bank;

        private Semaphore semaphore;

        public SaveMoney(Bank bank, Semaphore semaphore) {
            this.bank = bank;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            //TODO
//            synchronized (semaphore) {
//                if (semaphore.availablePermits() > 0) {
//                    LOGGER.info("[{}]进入银行，有位置，立即存钱", name);
//                } else {
//                    LOGGER.info("[{}]进入银行，无位置，排队等待", name);
//                }
//            }
            //TODO
            try {
                //semaphore.acquire();
                //LOGGER.info("[{}]准备存钱，availablePermits={}", name, semaphore.availablePermits());
                synchronized (bank) {
                    bank.save(10);
                    LOGGER.info("[{}]存钱后，银行存款：{}", name, bank.getAccount());
                }
//                Sleeps.seconds(3);
                //LOGGER.info("[{}]存钱完毕，离开银行", name);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                //semaphore.release();
            }
        }
    }

    public void test() {
        Bank bank = new Bank();
        Semaphore semaphore = new Semaphore(PERMITS);
        for (int i = 0; i < N; i++) {
            Thread t = new Thread(new SaveMoney(bank, semaphore));
            t.start();
        }
    }

    public static void main(String[] args) {
        Semaphore2_Main test = new Semaphore2_Main();
        test.test();
    }
}
