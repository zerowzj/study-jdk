package study.dyproxy.cglib.callback;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Dispatcher;

@Slf4j
public class DispatcherCallback implements Dispatcher {

    @Override
    public Object loadObject() throws Exception {
        return null;
    }
}
