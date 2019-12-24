package study.dyproxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloImpl implements Hello {

    public void sayHi(String name) {
        log.info("hi, {}", name);
    }

    public void sayBye(String name) {
        log.info("bye, {}", name);
    }
}
