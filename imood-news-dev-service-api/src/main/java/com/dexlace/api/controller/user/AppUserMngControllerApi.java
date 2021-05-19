package com.dexlace.api.controller.user;


import com.dexlace.common.result.GraceIMOODJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author: xiaogongbing
 * @Description: 这一层只写接口
 * @Date: 2021/4/28
 */

@Api(value = "用户管理相关的接口定义", tags = {"用户管理相关功能的controller"})
@RequestMapping("appUser")
public interface AppUserMngControllerApi {
    @PostMapping("queryAll")
    @ApiOperation(value = "查询所有网站用户", notes = "查询所有网站用户", httpMethod = "POST")
    GraceIMOODJSONResult queryAll(@RequestParam String nickname,
                                         @RequestParam Integer status,
                                         @RequestParam Date startDate,
                                         @RequestParam Date endDate,
                                         @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                    @RequestParam Integer page,
                                         @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                    @RequestParam Integer pageSize);
    @PostMapping("userDetail")
    @ApiOperation(value = "查看用户详情信息", notes = "查看用户详情信息", httpMethod = "POST")
    GraceIMOODJSONResult userDetail(@RequestParam String userId);



    @PostMapping("freezeUserOrNot")
    @ApiOperation(value = "冻结用户，或解除封号", notes = "冻结用户，或解除封号", httpMethod = "POST")
    GraceIMOODJSONResult freezeUserOrNot(@RequestParam String userId, @RequestParam Integer doStatus);

}

