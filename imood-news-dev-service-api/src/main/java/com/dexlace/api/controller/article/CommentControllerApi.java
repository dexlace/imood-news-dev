package com.dexlace.api.controller.article;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.CommentReplyBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/16
 */
@Api(value = "文章详情页的评论业务", tags = {"文章详情页的评论业务controller"})
@RequestMapping("comment")
public interface CommentControllerApi {

    @PostMapping("/createComment")
    @ApiOperation(value = "用户留言，或回复留言", notes = "用户留言，或回复留言", httpMethod = "POST")
    GraceIMOODJSONResult createComment(@RequestBody @Valid CommentReplyBO commentReplyBO, BindingResult result);


    @GetMapping("/counts")
    @ApiOperation(value = "用户评论数查询", notes = "用户评论数查询", httpMethod = "GET")
    GraceIMOODJSONResult commentCounts(@RequestParam String articleId);


    @GetMapping("/list")
    @ApiOperation(value = "查询某文章的所有评论列表", notes = "查询某文章的所有评论列表", httpMethod = "GET")
    GraceIMOODJSONResult list(@RequestParam String articleId,
                              @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                              @RequestParam Integer page,
                              @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                              @RequestParam Integer pageSize);

    @PostMapping("/mng")
    @ApiOperation(value = "查询我的评论管理列表", notes = "查询我的评论管理列表", httpMethod = "POST")
    GraceIMOODJSONResult mng(@RequestParam String writerId,
                             @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                             @RequestParam Integer page,
                             @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                             @RequestParam Integer pageSize);

    @PostMapping("/delete")
    @ApiOperation(value = "用户评论数查询", notes = "用户评论数查询", httpMethod = "POST")
    GraceIMOODJSONResult delete(@RequestParam String writerId,
                                @RequestParam String commentId);


}

