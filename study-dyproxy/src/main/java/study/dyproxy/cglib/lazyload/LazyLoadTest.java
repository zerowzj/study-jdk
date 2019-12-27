package study.dyproxy.cglib.lazyload;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LazyLoadTest {

    @Test
    public void test() {
        Father father = new Father();
        log.info("name={}, age={}", father.getName(), father.getAge());
        Son son = father.getSon(); //访问延迟加载对象
//        log.info("name={}, age={}", son.getName(), son.getAge());
//        //当再次访问延迟加载对象时,就不会再执行回调了
//        log.info("name={}, age={}", son.getName(), son.getAge());
    }
}
