package study.dyproxy.cglib.demo;

import net.sf.cglib.proxy.Enhancer;
import study.dyproxy.HelloImpl;

public class DemoMain {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloImpl.class);
        enhancer.setCallback(new CglibProxyInterceptor());
        HelloImpl proxy = (HelloImpl) enhancer.create();
        proxy.sayBye("sfsdfsd");
    }
}
