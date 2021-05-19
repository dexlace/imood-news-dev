package com.dexlace.article.controller;

import com.dexlace.api.controller.user.HelloControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/28
 */
@RestController
public class HelloController implements HelloControllerApi {



    final static Logger logger = LoggerFactory.getLogger(HelloController.class);
    public Object hello() {

        logger.debug("debug: hello~");
        logger.info("info: hello~");
        logger.warn("warn: hello~");
        logger.error("error: hello~");
//        return IMOODJSONResult.ok("hello");
        return GraceIMOODJSONResult.ok();
    }

}

