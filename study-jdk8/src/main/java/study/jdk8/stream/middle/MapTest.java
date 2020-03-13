package study.jdk8.stream.middle;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * map 操作符要求输入一个Function的函数是接口实例
 * 功能是将T类型转换成R类型的
 */
@Slf4j
public class MapTest {

    @Test
    public void test() {
        List<Integer> lenLt = new ArrayList();

        Stream.of("apple", "banana", "orange", "waltermaleon", "grape")
                .map(e -> e.length())
                .forEach(e -> lenLt.add(e));

        log.info("{}", lenLt);
    }
}
