package study.jdk8.stream.intermediate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class DistintTest {

    @Test
    public void test1() {
        List<String> array = Arrays.asList("abc", "abc", "abc", "efg", "abcd", "jkl");
        array.stream()
                .distinct()
                .forEach(e -> log.info("==> {}", e));
    }
}
