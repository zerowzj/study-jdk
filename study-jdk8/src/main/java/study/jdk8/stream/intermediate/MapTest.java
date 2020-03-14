package study.jdk8.stream.intermediate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * map 操作符要求输入一个Function的函数是接口实例
 * 功能是将T类型转换成R类型的
 */
@Slf4j
public class MapTest {

    @Test
    public void test1() {
        List<Integer> lenLt = Stream.of("apple", "banana", "orange", "waltermaleon", "grape")
                .map(e -> e.length())
                .collect(Collectors.toList());
        log.info("{}", lenLt);
    }

    @Test
    public void test2() {
        User u1 = new User("123");
        User u2 = new User("abc");
        User u3 = new User("123");
        List<User> userLt = Arrays.asList(u1, u2, u3);
        userLt.stream().map(User::getCode)
                .distinct()
                .forEach(e -> {
                    log.info(e);
                });
    }

    @Test
    public void test3() {
        User u1 = new User("123");
        User u2 = new User("abc");
        User u3 = new User("123");
        List<User> userLt = Arrays.asList(u1, u2, u3);

        Map<String, User> userMap = new HashMap<>();
        userLt.stream()
                .map(user -> {
                    String code = user.getCode();
                    userMap.put(code, user);
                    return code;
                })
                .distinct()
                .forEach(e -> log.info(e));
        log.info("{}", userMap);
    }

    @Test
    public void test4() {
        User u1 = new User("abc");
        User u2 = new User("123");
        List<User> userLt = Arrays.asList(u1, u2);
        List<Student> stuLt = userLt.stream()
                .map(user -> {
                    Student stu = new Student(user.getCode());
                    return stu;
                })
                .collect(Collectors.toList());
        log.info("{}", stuLt);
    }

    @Data
    @AllArgsConstructor
    static class User {

        private String code;
    }

    @Data
    @AllArgsConstructor
    static class Student {

        private String code;
    }
}
