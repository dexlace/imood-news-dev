package com.dexlace.api.controller.admin;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.SaveFriendLinkBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
@Api(value = "首页友情链接维护", tags = {"首页友情链接维护controller"})
@RequestMapping("friendLinkMng")
public interface FriendLinkMngControllerApi {

    @PostMapping("saveOrUpdateFriendLink")
    @ApiOperation(value = "新增或修改友情链接", notes = "新增或修改友情链接", httpMethod = "POST")
    GraceIMOODJSONResult saveOrUpdateFriendLink(@RequestBody @Valid SaveFriendLinkBO saveFriendLinkBO,
                                                       BindingResult result);

    @PostMapping("getFriendLinkList")
    @ApiOperation(value = "查询友情链接列表", notes = "查询友情链接列表", httpMethod = "POST")
    GraceIMOODJSONResult getFriendLinkList();


    @PostMapping("delete")
    @ApiOperation(value = "删除友情链接", notes = "删除友情链接", httpMethod = "POST")
    GraceIMOODJSONResult delete(@RequestParam String linkId);

    @GetMapping("portal/list")
    @ApiOperation(value = "首页查询友情链接列表", notes = "首页查询友情链接列表", httpMethod = "GET")
    GraceIMOODJSONResult getPortalFriendLinkList();

}


