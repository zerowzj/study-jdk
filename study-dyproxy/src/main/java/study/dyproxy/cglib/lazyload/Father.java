package study.dyproxy.cglib.lazyload;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

@Slf4j
@Setter
@Getter
public class Father {

    private String name;

    private int age;

    private Son son;

    public Father() {
        this.name = "wang";
        this.age = 38;
        this.son = loadSon();
    }

    protected Son loadSon() {
        log.info("Father loadSon()...");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Son.class);
        return (Son) enhancer.create(Son.class, new SonLazyLoader());
    }
}
