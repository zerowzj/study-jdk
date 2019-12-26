package study.dyproxy.cglib.demo;

import net.sf.cglib.proxy.Enhancer;
import study.dyproxy.Hello;
import study.dyproxy.HelloImpl;

/**
 * 1.JDK动态代理是实现了被代理对象的接口，Cglib是继承了被代理对象。
 * 2.JDK和Cglib都是在运行期生成字节码，JDK是直接写Class字节码，Cglib使用ASM框架写Class字节码，Cglib代理实现更复杂，生成代理类比JDK效率低。
 * 3.JDK调用代理方法，是通过反射机制调用，Cglib是通过FastClass机制直接调用方法，Cglib执行效率更高。
 */
public class DemoMain {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloImpl.class);
        enhancer.setCallback(new CglibProxyInterceptor());
        HelloImpl proxy = (HelloImpl) enhancer.create();
        proxy.sayHi("sfsdfsd");
        proxy.sayBye("sfsdfsd");
    }
}
