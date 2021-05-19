package com.dexlace.api.controller.user;

import com.dexlace.common.result.GraceIMOODJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/14
 */
@Api(value = "粉丝管理", tags = {"粉丝管理controller"})
@RequestMapping("fans")
public interface MyFansControllerApi {
    @PostMapping("isMeFollowThisWriter")
    @ApiOperation(value = "查询当前用户是否关注作家", notes = "查询当前用户是否关注作家", httpMethod = "POST")
    GraceIMOODJSONResult isMeFollowThisWriter(@RequestParam String writerId,
                                                     @RequestParam String fanId);


    @PostMapping("follow")
    @ApiOperation(value = "关注作家，成为粉丝", notes = "关注作家，成为粉丝", httpMethod = "POST")
    GraceIMOODJSONResult follow(@RequestParam String writerId, @RequestParam String fanId);


    @PostMapping("unfollow")
    @ApiOperation(value = "取消关注，作家损失粉丝", notes = "取消关注，作家损失粉丝", httpMethod = "POST")
    GraceIMOODJSONResult unfollow(@RequestParam String writerId, @RequestParam String fanId);



    @PostMapping("queryAll")
    @ApiOperation(value = "查询我的所有粉丝", notes = "查询我的所有粉丝", httpMethod = "POST")
    GraceIMOODJSONResult queryAll(@RequestParam String writerId,
                                    @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                    @RequestParam Integer page,
                                    @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                    @RequestParam Integer pageSize);

    @PostMapping("queryRatio")
    @ApiOperation(value = "查询粉丝男女比例", notes = "查询粉丝男女比例", httpMethod = "POST")
    GraceIMOODJSONResult queryRatio(@RequestParam String writerId);


    @PostMapping("queryRatioByRegion")
    @ApiOperation(value = "查询粉丝地域比例", notes = "查询粉丝地域比例", httpMethod = "POST")
    GraceIMOODJSONResult queryRatioByRegion(@RequestParam String writerId);


}

