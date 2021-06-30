package com.dexlace.api.controller.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/11
 */

@Api(value = "文章html controller", tags = {"文章html controller"})
@RequestMapping("article/html")
public interface ArticleHtmlControllerApi {


    @ApiOperation(value = "下载文章html", notes = "下载文章html", httpMethod = "GET")
    @GetMapping("/download")
    Integer download(String articleId, String articleMongoId) throws Exception;


    @ApiOperation(value = "删除文章html", notes = "删除文章html", httpMethod = "GET")
    @GetMapping("/delete")
    Integer delete(String articleId, String articleMongoId) throws Exception;


}
