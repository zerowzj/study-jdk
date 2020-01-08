package study.reflect.field;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import study.reflect.MyBean;
import study.reflect.Sex;

import java.lang.reflect.Field;

@Slf4j
public class Field1_Test {

    @Test
    public void getModifiers_test() {
        Field[] fields = MyBean.class.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            String name = field.getName();
            Class type = field.getType();
            log.info("modifiers={}, name={}, type={}", modifiers, name, type.getName());
        }
    }

    @Test
    public void get_test() throws Exception {
        Field[] fields = MyBean.class.getDeclaredFields();
        MyBean bean = new MyBean();
        bean.setName("wangzhj");
        bean.setAge(38);
        bean.setSex(Sex.MAN);
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = field.get(bean);
            log.info("{}={}", field.getName(), obj);
        }
    }

    @Test
    public void getAnnotation_test() throws Exception {
        Field field = null;
        Object obj = field.getAnnotations();
    }
}
