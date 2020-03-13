package study.jdk8.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Middle_Operation_Test {
    @Test
    public void map_test() {
        List<String> data = Arrays.asList("123", "abc");
        data.stream()
                .forEach(System.out::println);
    }


    @Test
    public void flatmap_test() {
        List<String> data = Arrays.asList("123", "abc");
        data.stream()
                .forEach(System.out::println);
    }

    public void limit_test() {

    }

    public void distint_test() {
    }

    /**
     * filter 方法用于通过设置的条件过滤出元素
     */
    @Test
    public void filter_test() {
        List<String> array = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = array.stream()
                .filter(str -> str.isEmpty())
                .count();
        log.info("count={}", count);
    }

    public void peek_test() {
    }

    public void skip_test() {
    }

    public void sorted_test() {
    }
}
