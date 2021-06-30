package com.dexlace.html.consumer;

import com.dexlace.api.config.RabbitMqConfig;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/11
 */
@Component
public class RabbitMQConsumer {

    @Autowired
    private ArticleHtmlProcessor articleHtmlProcessor;

    @RabbitListener(queues = {RabbitMqConfig.QUEUE_DOWNLOAD_HTML})
    public void watchQueue(String payload, Message message) throws Exception {

        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        // 找到对应的routingKey，然后获取articleId和对应的mongoId
        if (routingKey.equalsIgnoreCase("article.download")){
            String[] strings = payload.split("-");
            articleHtmlProcessor.download(strings[0],strings[1]);
        }
        if (routingKey.equalsIgnoreCase("article.delete")){
            String[] strings = payload.split("-");
            articleHtmlProcessor.delete(strings[0],strings[1]);
        }

    }
}
