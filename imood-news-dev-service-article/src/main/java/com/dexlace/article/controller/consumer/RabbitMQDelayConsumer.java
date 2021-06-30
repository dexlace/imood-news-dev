package com.dexlace.article.controller.consumer;

import com.dexlace.api.config.RabbitMqDelayConfig;
import com.dexlace.article.service.ArticleService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/12
 */
@Component
public class RabbitMQDelayConsumer {

    @Autowired
    private ArticleService articleService;


    @RabbitListener(queues={RabbitMqDelayConfig.QUEUE_DELAY})
    public void watchQueue(String payload, Message message){
        String routingKey=message.getMessageProperties().getReceivedRoutingKey();
        if(routingKey.equalsIgnoreCase("article.delay.publish")){
            String articleId=payload;
            articleService.updateAppointToPublishRabbitmq(articleId);
        }

    }
}
