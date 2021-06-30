package com.dexlace.article.controller;

import com.dexlace.api.config.RabbitMqConfig;
import com.dexlace.api.controller.user.HelloControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/28
 */
@RestController
@RequestMapping("producer")
public class HelloController implements HelloControllerApi {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @GetMapping("/hello")
    public Object hello() {


        /**
         * 路由规则：routing key
         * display.*.*  -> *表示一个占位符
         *  匹配例子
         *  display.do.di  匹配
         *  display.do.di.dp 不匹配
         *
         *
         *  display.# ->代表多个占位符
         *  匹配例子
         *          *  display.do.di  匹配
         *          *  display.do.di.dp 匹配
         */


        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ARTICLE,
                "article.hello",
                "生产消息");


        return GraceIMOODJSONResult.ok();
    }





}

