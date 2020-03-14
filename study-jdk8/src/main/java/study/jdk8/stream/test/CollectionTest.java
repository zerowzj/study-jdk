package study.jdk8.stream.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class CollectionTest {

    @Test
    public void TEST() {
        List<String> data = Arrays.asList("1123", "abb");
        data.stream().forEach(s ->log.info(s));
    }
}
