package study.reflect.field;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;

@Slf4j
public class Field1_Test {

    @Test
    public void getModifiers_test() throws Exception {
        Field field = null;
        int modifiers = field.getModifiers();
        String name = field.getName();
        field.getType();
        log.info("modifiers={}, name={}, type={}", modifiers, name);
    }

    @Test
    public void get_test() throws Exception {
        Field field = null;
        Object obj = field.get(null);
    }

    @Test
    public void getAnnotation_test() throws Exception {
        Field field = null;
        Object obj = field.getAnnotations();
    }
}
