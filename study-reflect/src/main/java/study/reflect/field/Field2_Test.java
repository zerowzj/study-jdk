package study.reflect.field;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import study.reflect.MyBean;
import study.reflect.NotNull;

import java.lang.reflect.Field;

@Slf4j
public class Field2_Test {

    @Test
    public void isAccessible_test() {
        Field field = null;
        boolean isAccessible = field.isAccessible();
        log.info("isAccessible={}", isAccessible);
    }

    @Test
    public void isAnnotationPresent_test() {
        Field[] fields = MyBean.class.getDeclaredFields();
        for (Field field : fields) {
            boolean flag = field.isAnnotationPresent(NotNull.class);
            if (flag) {
                log.info("{}", field.getName());
            }
        }
    }
}
