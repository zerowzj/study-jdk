package study.dyproxy.cglib.demo;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class CglibProxyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("执行前...");
        Object object = methodProxy.invokeSuper(sub, objects);
        log.info("执行后...");
        return object;
    }
}
