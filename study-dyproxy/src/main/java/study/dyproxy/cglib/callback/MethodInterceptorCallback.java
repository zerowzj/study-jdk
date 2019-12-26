package study.dyproxy.cglib.callback;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class MethodInterceptorCallback implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method,
                            Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("before invoke........");
        methodProxy.invokeSuper(obj, objects);
        log.info("after invoke.........");
        return null;

    }
}
