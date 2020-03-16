package study.jdk8.return_;

import lombok.extern.slf4j.Slf4j;

/**
 * 在try中执行到return语句时，不会真正的return，即只是会计算return中的表达式，
 * 之后将结果保存在一个临时栈中，接着执行finally中的语句，最后才会从临时栈中取出之前的结果返回
 */
@Slf4j
public class TryReturn_Main {

    public static int test1() {
        int i = 0;
        try {
            i = 1;
            return i; //return
        } catch (Exception ex) {

        } finally {
            log.info("finally");
            i = 3;
        }
        //
        return i;
    }

    public static int test2() {
        int i = 0;
        try {
            i = 1;
            if (1 == 1) {
                throw new RuntimeException("exception");
            }
            return i; //return
        } catch (Exception ex) {
            i = 2;
//            return i; //return
        } finally {
            i = 3;
        }
        return i;
    }

    public static int test3() {
        int i = 0;
        try {
            i = 1;
            if (1 == 1) {
                throw new RuntimeException("exception");
            }
            return i; //return
        } catch (Exception ex) {
            i = 2;
            return i; //return
        } finally {
            i = 3;
            return i; //return
        }
    }

    public static void main(String[] args) {
        log.info("test1= {}", test1());
        log.info("test2= {}", test2());
        log.info("test3= {}", test3());
    }
}
