package study.dyproxy.jdk;

import study.dyproxy.Hello;
import study.dyproxy.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DemoMain {

    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        InvocationHandler handler = new ProxyHandler(hello);
        Hello proxyHello = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);
        proxyHello.sayHi("wangzhj");
    }
}
