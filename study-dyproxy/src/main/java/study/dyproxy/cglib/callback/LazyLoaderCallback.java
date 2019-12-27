package study.dyproxy.cglib.callback;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.LazyLoader;

@Slf4j
public class LazyLoaderCallback implements LazyLoader {
    @Override
    public Object loadObject() throws Exception {
        return null;
    }
}
