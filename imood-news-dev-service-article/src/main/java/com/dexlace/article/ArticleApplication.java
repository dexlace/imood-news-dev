package com.dexlace.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@SpringBootApplication
@MapperScan(basePackages = "com.dexlace.article.mapper")
@EnableEurekaClient
// 扫描所有包以及相关组件包
@ComponentScan({"com.dexlace","org.n3r.idworker"})
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class,args);
    }
}
