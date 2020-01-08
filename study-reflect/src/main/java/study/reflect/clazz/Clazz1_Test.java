package study.reflect.clazz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Clazz1_Test {

    @Test
    public void get_test() {
        Class clazz = Object.class;
        int i = 0x11;
        log.info("{}", i);
    }

    @Test
    public void getClass_test() {
        Class clazz = this.getClass();
        int i = 0x11;
        log.info("{}", i);
    }

    @Test
    public void getClass1_test() {
        Class clazz = this.getClass();
        int i = 0x11;
        log.info("{}", i);
    }
}
