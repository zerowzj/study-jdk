package study.jdk.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition2_Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Condition2_Main.class);

    private ReentrantLock lock = new ReentrantLock();

    private Condition full = lock.newCondition();

    private Condition empty = lock.newCondition();

    public static void main(String[] args) {

    }
}
