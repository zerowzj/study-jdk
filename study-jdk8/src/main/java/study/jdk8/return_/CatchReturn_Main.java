package study.jdk8.return_;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CatchReturn_Main {

    //try{} catch(){} finally{} return
    public static int test1() {
        int i = 1;
        try {
            i = 2;
        } catch (Exception ex) {

        } finally {
            i = 4;
        }
        //（1）
        return i;
    }

    //try{return} catch(){} finally{} return
    public static int test2() {
        int i = 1;
        try {
            i = 2;
            //（1）
            return i;
        } catch (Exception ex) {

        } finally {
            log.info("finally");
            i = 4;
        }
        //（2）
        return i;
    }

    public static void main(String[] args) {
        log.info("test1= {}", test1());
        log.info("test2= {}", test2());
    }
}
