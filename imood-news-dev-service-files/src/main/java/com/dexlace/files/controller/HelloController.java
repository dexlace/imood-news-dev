package com.dexlace.files.controller;

import com.dexlace.api.controller.user.HelloControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


        return GraceIMOODJSONResult.ok("hello files");
    }


}

