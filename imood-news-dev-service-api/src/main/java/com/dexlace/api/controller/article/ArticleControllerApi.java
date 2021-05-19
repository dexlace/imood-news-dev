package com.dexlace.api.controller.article;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.NewArticleBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@Api(value = "文章业务controller", tags = {"文章业务controller"})
@RequestMapping("article")
public interface ArticleControllerApi {

    @PostMapping("/createArticle")
    @ApiOperation(value = "用户发文", notes = "用户发文", httpMethod = "POST")
    GraceIMOODJSONResult createArticle(@RequestBody @Valid NewArticleBO newArticleBO, BindingResult result);


    @PostMapping("/queryMyList")
    @ApiOperation(value = "查询自己的所有文章列表", notes = "查询用户自己的所有文章列表", httpMethod = "POST")
    GraceIMOODJSONResult queryMyList(@RequestParam String userId,
                                     @RequestParam String keyword,
                                     @RequestParam Integer status,
                                     @RequestParam Date startDate,
                                     @RequestParam Date endDate,
                                     @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                     @RequestParam Integer page,
                                     @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                     @RequestParam Integer pageSize);


    @PostMapping("/queryAllList")
    @ApiOperation(value = "查询用户的所有文章列表", notes = "查询用户的所有文章列表", httpMethod = "POST")
    GraceIMOODJSONResult queryAllList(
            @RequestParam Integer status,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize);


    @PostMapping("/doReview")
    @ApiOperation(value = "管理员审核文章成功或者失败", notes = "管理员审核文章成功或者失败", httpMethod = "POST")
    GraceIMOODJSONResult doReview(
            @RequestParam String articleId,
            @RequestParam Integer passOrNot);


    @PostMapping("/delete")
    @ApiOperation(value = "用户删除文章", notes = "用户删除文章", httpMethod = "POST")
    GraceIMOODJSONResult delete(@RequestParam String userId,
                           @RequestParam String articleId);

    @PostMapping("/withdraw")
    @ApiOperation(value = "用户撤回文章", notes = "用户撤回文章", httpMethod = "POST")
    GraceIMOODJSONResult withdraw(@RequestParam String userId,
                             @RequestParam String articleId);




}

