package com.shengsu.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;


@SpringBootApplication(exclude = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ImportResource({"classpath:application-context.xml"})
public class Application {
    /**
     * @param args
     */
    public static void main(String[] args) {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
            SpringApplication.run(Application.class, args);
    }
}
