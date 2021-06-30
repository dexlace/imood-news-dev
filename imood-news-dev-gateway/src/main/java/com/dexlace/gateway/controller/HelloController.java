package com.dexlace.gateway.controller;

import com.dexlace.common.result.GraceIMOODJSONResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/13
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello(){
        return GraceIMOODJSONResult.ok();
    }
}
