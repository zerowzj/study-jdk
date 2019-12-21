package study;

import java.util.concurrent.TimeUnit;

public class Sleeps {

    public static void deep() {
        seconds(Integer.MAX_VALUE);
    }

    public static void seconds(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void minutes(long timeout) {
        try {
            TimeUnit.MINUTES.sleep(timeout);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void milliseconds(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
