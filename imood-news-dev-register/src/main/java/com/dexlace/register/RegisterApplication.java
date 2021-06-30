package com.dexlace.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/12
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class,
        RedisAutoConfiguration.class, RabbitAutoConfiguration.class})
@EnableEurekaServer     // 开启注册中心
public class RegisterApplication {
    public static void main(String[] args){
        SpringApplication.run(RegisterApplication.class,args);
    }
}
