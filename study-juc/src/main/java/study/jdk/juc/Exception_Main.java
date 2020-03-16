package study.jdk.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exception_Main {

    public static void main(String[] args) {
        log.info("{}", test());

    }

    public static int test() {
        int i = 1;
        try {
            i = 2;
            return i;
        } catch (Exception ex) {

        } finally {
            log.info("dfsadfas");
            i = 4;
            return i;
        }
    }

    public int test2() {
        int i = 1;
        try {
            i = 2;
        } catch (Exception ex) {
            i = 3;
            return i;
        } finally {
            i = 4;
        }
        return i;
    }
}
