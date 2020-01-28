package study.jcf;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LATest {

    @Test
    public void test(){
        List<String> list= Arrays.asList("a", "b", "c", "d");
        List<String> collect =list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(list);
    }
}
