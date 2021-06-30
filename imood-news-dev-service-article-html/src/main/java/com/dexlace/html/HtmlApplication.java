package com.dexlace.html;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/10
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 扫描所有包以及相关组件包
@ComponentScan({"com.dexlace","org.n3r.idworker"})
@EnableEurekaClient
public class HtmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(HtmlApplication.class,args);
    }
}

