package study.reflect.clazz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;

@Slf4j
public class Clazz3_Test {

    @Test
    public void getField_test()  throws Exception {
        Class clazz = Object.class;
        Field[] fields = clazz.getDeclaredFields();
        clazz.getDeclaredField("");
        clazz.getFields();
        clazz.getField("");
    }

    @Test
    public void getMethod_test()  throws Exception {
        Class clazz = this.getClass();
        clazz.getDeclaredMethods();
        clazz.getDeclaredMethod("",null);

        clazz.getMethods();
        clazz.getMethod("", null);
    }

    @Test
    public void getClass1_test() {
        Class clazz = this.getClass();
        int i = 0x11;
        log.info("{}", i);
    }
}
