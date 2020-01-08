package study.reflect.field;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;

@Slf4j
public class Field2_Test {

    @Test
    public void isAccessible_test() throws Exception {
        Field field = null;
        boolean isAccessible = field.isAccessible();
        log.info("isAccessible={}", isAccessible);
    }

    @Test
    public void isAnnotationPresent_test() throws Exception {
        Field field = null;
        boolean isAnnotationPresent = field.isAnnotationPresent(null);
        log.info("isAnnotationPresent={}", isAnnotationPresent);
    }

    @Test
    public void isSynthetic_test() throws Exception {
    }
}
