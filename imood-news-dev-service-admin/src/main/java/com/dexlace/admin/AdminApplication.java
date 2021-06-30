package com.dexlace.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@SpringBootApplication(exclude = RabbitAutoConfiguration.class)
@MapperScan(basePackages = "com.dexlace.admin.mapper")
// 扫描所有包以及相关组件包
@ComponentScan({"com.dexlace","org.n3r.idworker"})
@EnableEurekaClient
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
