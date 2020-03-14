package study.jdk8.stream.intermediate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import study.jdk8.stream.User;

import java.util.Arrays;
import java.util.List;

/**
 * filter 方法用于通过设置的条件过滤出元素
 */
@Slf4j
public class FilterTest {

    @Test
    public void test1() {
        List<String> data = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = data.stream()
                .filter(str -> str.isEmpty())
                .count();
        log.info("count={}", count);
    }

    @Test
    public void test2() {
        User u1 = new User("123");
        User u2 = new User("abc");
        User u3 = new User("xxx");
        List<User> data = Arrays.asList(u1, u2, u3);
        data.stream().filter(e -> {
            e.getCode();
            return true;
        }).forEach(e -> {
            log.info("{}", e);
        });
    }
}
