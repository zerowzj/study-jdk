package study.reflect;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class MyBean {

    @NotNull
    private String name;

    private int age;

    private Sex sex;
}
