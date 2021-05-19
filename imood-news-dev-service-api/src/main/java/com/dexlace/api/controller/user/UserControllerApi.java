package com.dexlace.api.controller.user;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.UpdateUserInfoBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */
@Api(value = "用户controller", tags = {"用户controller"})
@RequestMapping("user")
public interface UserControllerApi {
    @PostMapping("/getAccountInfo")
    @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息", httpMethod = "POST")
    GraceIMOODJSONResult getAccountInfo(@RequestParam String userId);

    @PostMapping("/getUserInfo")
    @ApiOperation(value = "获得用户基础信息", notes = "获得用户基础信息", httpMethod = "POST")
    GraceIMOODJSONResult getUserInfo(@RequestParam String userId);

    @PostMapping("/updateUserInfo")
    @ApiOperation(value = "完善用户信息", notes = "完善用户信息", httpMethod = "POST")
    GraceIMOODJSONResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBO updateUserInfoBO,
                                          BindingResult result);
    @GetMapping("/queryByIds")
    @ApiOperation(value = "根据用户id查询用户", notes = "根据用户id查询用户", httpMethod = "GET")
    GraceIMOODJSONResult queryByIds(@RequestParam String userIds);



}


