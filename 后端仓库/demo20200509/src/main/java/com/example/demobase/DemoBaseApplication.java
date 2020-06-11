package com.example.demobase;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Administrator
 * @EnableAsync 允许异步调用
 * @EnableAspectJAutoProxy 开启AOP
 */
@Slf4j
@EnableAsync(proxyTargetClass=true)
@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("com.example.demoBase.mapper")
public class DemoBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoBaseApplication.class, args);
        log.info("+++++++++++++++ Service Start! +++++++++++++++");
    }
}
