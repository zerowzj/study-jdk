package study.jdk8.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Middle_Operation_Test {

    @Test
    public void test() {
        List<String> data = Arrays.asList("123", "abc");
        data.stream()
                .forEach(System.out::println);
    }
}
