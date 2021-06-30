package com.dexlace.api.controller.article;

import com.dexlace.common.result.GraceIMOODJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@Api(value = "门户站点文章业务controller", tags = {"门户站点文章业务controller"})
@RequestMapping("portal/article")
public interface ArticlePortalControllerApi {




    @GetMapping("es/list")
    @ApiOperation(value = "通过elasticsearch来进行首页查询文章列表", notes = "首页查询文章列表", httpMethod = "GET")
    GraceIMOODJSONResult eslist(@RequestParam String keyword,
                                  @RequestParam Integer category,
                                  @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                  @RequestParam Integer page,
                                  @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                  @RequestParam Integer pageSize);







    @GetMapping("list")
    @ApiOperation(value = "首页查询文章列表", notes = "首页查询文章列表", httpMethod = "GET")
    GraceIMOODJSONResult list(@RequestParam String keyword,
                                     @RequestParam Integer category,
                                     @RequestParam Integer page,
                                     @RequestParam Integer pageSize);


    @GetMapping("hotList")
    @ApiOperation(value = "首页查询热闻列表", notes = "首页查询热闻列表", httpMethod = "GET")
    GraceIMOODJSONResult hotList();


    @GetMapping("queryArticleListOfWriter")
    @ApiOperation(value = "首页查询作者文章列表", notes = "首页查询作者文章列表", httpMethod = "GET")
    GraceIMOODJSONResult queryArticleListOfWriter(@RequestParam String writerId,
                                                  @RequestParam Integer page,
                                                  @RequestParam Integer pageSize);




    @GetMapping("queryGoodArticleListOfWriter")
    @ApiOperation(value = "首页查询作者近期佳文", notes = "首页查询作者近期佳文", httpMethod = "GET")
    GraceIMOODJSONResult queryGoodArticleListOfWriter(@RequestParam String writerId);




    @GetMapping("detail")
    @ApiOperation(value = "首页查询文章详情", notes = "首页查询文章详情", httpMethod = "GET")
    GraceIMOODJSONResult detail(@RequestParam String articleId);


    @PostMapping("readArticle")
    @ApiOperation(value = "阅读文章，累加阅读量", notes = "阅读文章，累加阅读量", httpMethod = "POST")
    GraceIMOODJSONResult readArticle(@RequestParam String articleId, HttpServletRequest request);



    @GetMapping("readCounts")
    @ApiOperation(value = "获得文章阅读量", notes = "获得文章阅读量", httpMethod = "GET")
    Integer readCounts(@RequestParam String articleId);


}
