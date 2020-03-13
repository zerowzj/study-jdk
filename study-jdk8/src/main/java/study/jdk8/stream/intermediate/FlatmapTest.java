package study.jdk8.stream.intermediate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class FlatmapTest {

    @Test
    public void test1() {
        List<String> data = Arrays.asList("a,b,c", "1,2,3");
        Stream<String> stream = data.stream().flatMap(s -> {
            //将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        });
        stream.forEach(System.out::println);
    }
}