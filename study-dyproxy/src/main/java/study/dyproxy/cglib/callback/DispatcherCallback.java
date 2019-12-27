package study.dyproxy.cglib.callback;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Dispatcher;

/**
 * 每一次调用代理方法的时候，都会调用一次Dispatcher的loadObject获取对象
 * lazy则会缓存下来
 */
@Slf4j
public class DispatcherCallback implements Dispatcher {

    @Override
    public Object loadObject() throws Exception {
        return null;
    }
}
