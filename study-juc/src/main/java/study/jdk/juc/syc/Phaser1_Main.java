package study.jdk.juc.syc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Phaser;

public class Phaser1_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Phaser1_Main.class);

    private static final int PARTIES = 3;

    private static final int PHASES = 4;

    public static void main(String[] args) {
        Phaser phaser = new Phaser(PARTIES) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                LOGGER.info("====== Phase : " + phase + " ======");
                return registeredParties == 0;
            }
        };
        for (int i = 0; i < PARTIES; i++) {
            int no = i + 1;
            Thread t = new Thread(() -> {
                for (int phase = 0; phase < PHASES; phase++) {
                    LOGGER.info(String.format("Thread %s, phase %s", no, phase));
                    phaser.arriveAndAwaitAdvance();
                }
            });
            t.start();
        }
    }
}
