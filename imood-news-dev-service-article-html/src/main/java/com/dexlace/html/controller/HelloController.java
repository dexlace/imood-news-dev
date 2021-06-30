package com.dexlace.html.controller;

import com.dexlace.api.controller.user.HelloControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/10
 */
@RestController
public class HelloController implements HelloControllerApi {
    @Override
    public Object hello() {
        return GraceIMOODJSONResult.ok();
    }
}
