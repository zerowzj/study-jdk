package study.hystrix;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {
        CommandOrder commandPhone = new CommandOrder("手机");
        CommandOrder command = new CommandOrder("电视");
        //阻塞方式执行
        String execute = commandPhone.execute();
        log.info("execute=[{}]", execute);
        //异步非阻塞方式
        Future<String> queue = command.queue();
        String value = queue.get(200, TimeUnit.MILLISECONDS);
        log.info("value=[{}]", value);

        CommandUser commandUser = new CommandUser("张三");
        String name = commandUser.execute();
        log.info("name=[{}]", name);
    }
}
