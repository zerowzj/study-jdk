package study.dyproxy.cglib.lazyload;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.LazyLoader;

@Slf4j
public class SonLazyLoader implements LazyLoader {

    @Override
    public Object loadObject() throws Exception {
        log.info("LazyLoader loadObject()...");
        Son son = new Son();
        son.setName("xiao wang");
        son.setAge(1);
        return son;
    }
}
