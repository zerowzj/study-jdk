package study.dyproxy.cglib.callback;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class InvocationHandlerCallback implements InvocationHandler {

    /**
     * invocationHandler的invoke方法传入的method和proxy都是代理本身对象
     * 切忌重复调用，会循环调用
     *
     * @param proxy  代理类
     * @param method 代理类内部的方法
     * @param args   参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invocationHandlerCallback Before....");
        method.invoke(proxy.getClass().getSuperclass().newInstance(), args);
        //会无限循环
        //method.invoke(proxy, args);
        log.info("invocationHandlerCallback after....");
        return null;
    }
}
