package study.reflect.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import study.reflect.MyBean;

import java.lang.reflect.Method;

@Slf4j
public class Method1_Test {


    @Test
    public void getName_test() {
        Method[] methods = MyBean.class.getDeclaredMethods();
        for (Method method : methods) {
            log.info("{}", method.getName());
        }
    }

    @Test
    public void getReturnType_test() {
        Method[] methods = MyBean.class.getDeclaredMethods();
        for (Method method : methods) {
            log.info("{} {}()", method.getReturnType().getSimpleName(), method.getName());
        }
    }


    @Test
    public void getParameters_test() {
        Method[] methods = MyBean.class.getDeclaredMethods();
        for (Method method : methods) {
            log.info("{}: ", method.getName());
            Class[] clazzs = method.getParameterTypes();
            for (Class clazz : clazzs) {
                log.info("===>{}", clazz.getName());
            }
        }
    }

    @Test
    public void getP1arameters_test() {
        Method[] methods = MyBean.class.getDeclaredMethods();
        for (Method method : methods) {
            log.info("{}: ", method.getName());
        }
    }
}
