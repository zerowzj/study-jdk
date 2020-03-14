package study.jdk8.stream.intermediate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * filter 方法用于通过设置的条件过滤出元素
 */
@Slf4j
public class FilterTest {

    @Test
    public void filter_test() {
        List<String> array = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = array.stream()
                .filter(str -> str.isEmpty())
                .count();
        log.info("count={}", count);

    }

    @Test
    public void f_test(){
        List<Integer> intLt = new ArrayList();
        Stream.of(1, 2, 3, 1, 2, 5, 6, 7, 8, 0, 0, 1, 2, 3, 1)
                .filter(e -> e >= 5)
                .forEach(e -> intLt.add(e));

        log.info("{}", intLt);
    }
}
