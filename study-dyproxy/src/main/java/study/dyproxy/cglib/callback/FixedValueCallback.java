package study.dyproxy.cglib.callback;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.FixedValue;

@Slf4j
public class FixedValueCallback implements FixedValue {

    @Override
    public Object loadObject() throws Exception {
        log.info("this is fixvalue callback .....    overwrite the code....");
        return true;
    }
}
