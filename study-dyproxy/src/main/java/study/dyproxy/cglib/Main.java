package study.dyproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import study.dyproxy.Hello;
import study.dyproxy.HelloImpl;

public class Main {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloImpl.class);
        HelloImpl proxy = (HelloImpl) enhancer.create();
        proxy.sayBye("sfsdfsd");
    }
}
