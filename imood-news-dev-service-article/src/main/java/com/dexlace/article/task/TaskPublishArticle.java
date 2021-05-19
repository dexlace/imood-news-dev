package com.dexlace.article.task;

import com.dexlace.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
//@Configuration      //  1. 标记配置类注入容器 这个方法不好
//@EnableScheduling   //  2. 开启定时任务
public class TaskPublishArticle {

    @Autowired
    private ArticleService articleService;

    //3. 添加定时任务
    @Scheduled(cron = "0/3 * * * * ?")
    private void publishArticle() {
        // 4 修改文章定时状态改为即时状态
        articleService.updateAppointToPublish();
    }

}


