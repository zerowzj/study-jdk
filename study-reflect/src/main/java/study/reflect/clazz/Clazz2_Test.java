package study.reflect.clazz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;

@Slf4j
public class Clazz2_Test {

    /**
     * 是否是数组
     */
    @Test
    public void isPrimitive_test() {
        Class clazz = Integer.class;
        boolean isPrimitive = clazz.isPrimitive();
        log.info("isPrimitive={}", isPrimitive);
    }

    /**
     * 是否是数组
     */
    @Test
    public void isArray_test() {
        String[] arr = new String[]{"1", "2"};
        Class clazz = arr.getClass();
        boolean isArray = clazz.isArray();
        log.info("isArray={}", isArray);
    }

    /**
     * 是否是接口
     */
    @Test
    public void isInterface_test() {
        Class clazz = ArrayList.class;
        boolean isInterface = clazz.isInterface();
        log.info("isInterface={}", isInterface);
    }

    /**
     * 是否是注解
     */
    @Test
    public void isAnnotation_test() {
        Class clazz = Override.class;
        boolean isAnnotation = clazz.isAnnotation();
        log.info("isAnnotation={}", isAnnotation);
    }

    @Test
    public void isAnnotationPresent_test() {
        Class clazz = Override.class;
        boolean isAnnotationPresent = clazz.isAnnotationPresent(Object.class);
        log.info("isAnnotationPresent={}", isAnnotationPresent);
    }
}
