package com.dexlace.api.controller.user;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.RegisterLoginBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */

@Api(value = "用户注册登录", tags = {"用户注册登录的controller"})
@RequestMapping("passport")
public interface PassportControllerApi {
    @ApiOperation(value = "获得短信验证码", notes = "获得短信验证码", httpMethod = "GET")
    @GetMapping("/getSMSCode")
    GraceIMOODJSONResult getSMSCode(@RequestParam String mobile, HttpServletRequest request);

    @ApiOperation(value = "一键注册登录接口", notes = "一键注册登录接口", httpMethod = "POST")
    @PostMapping("/doLogin")
    GraceIMOODJSONResult doLogin(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestBody @Valid RegisterLoginBO registerBO,
                                 BindingResult result);

    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    GraceIMOODJSONResult logout(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam String userId);

}
