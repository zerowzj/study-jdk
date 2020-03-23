package study.jcf.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class List_Test {

    @Test
    public void asList_test() {
        String[] array = {"Apple", "Banana", "Orange"};
        List<String> list = Arrays.asList(array);
        //asList()的返回对象是一个Arrays内部类，并没有实现集合的修改方法
        //Arrays.asList()体现的是适配器模式，只是转换接口，后台的数据仍是数组
        //第一种情况：list.add()，运行是异常
        //第二种情况：str[0] = ""，那么list.get(0)也随之修改
        list.add("www");
    }

    @Test
    public void test1() {
        String[] myArray = {"Apple", "Banana", "Orange"};
        List<String> myList = Arrays.asList(myArray);
        List<String> t = new ArrayList<>(myList);
        t.add("fdsafasdf");
    }


    @Test
    public void stream_test() {
        String[] myArray = {"Apple", "Banana", "Orange"};
        List<String> myList = Arrays.asList(myArray);
        List<String> t = new ArrayList<>(myList);
        t.add("fdsafasdf");
    }
}
