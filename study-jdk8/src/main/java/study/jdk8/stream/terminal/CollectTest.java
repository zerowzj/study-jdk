package study.jdk8.stream.terminal;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CollectTest {

    @Test
    public void test1() {
        List<String> data = Stream.of("abc", "123", "", "adsaf")
                .collect(Collectors.toList());
        data.forEach(e ->{
            log.info(e);
        });
    }
}
