package com.dexlace.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/28
 */
// 扫描 mybatis 通用 mapper 所在的包
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, RabbitAutoConfiguration.class})
@EnableEurekaClient
@MapperScan(basePackages = "com.dexlace.user.mapper")
// 扫描所有包以及相关组件包
@ComponentScan({"com.dexlace","org.n3r.idworker"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}


