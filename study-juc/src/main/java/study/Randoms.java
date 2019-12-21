package study;

import java.util.concurrent.ThreadLocalRandom;

public class Randoms {

    public static int nextInt(int bound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(bound);
    }

    public static long nextLong(int bound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextLong(bound);
    }
}
