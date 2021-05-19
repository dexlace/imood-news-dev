package com.dexlace.article.service;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.bo.NewArticleBO;
import com.dexlace.model.entity.Category;

import java.util.Date;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
public interface ArticleService {
    /**
     * 发布文章
     */
    void createArticle(NewArticleBO newArticleBO, Category category);


    /**
     * 更新定时发布为即时发布
     */
    void updateAppointToPublish();


    /**
     * 用户查询自己的文章列表
     */
    PagedGridResult queryMyArticleList(String userId, String keyword,
                                       Integer status,
                                       Date startDate, Date endDate,
                                       Integer page, Integer pageSize);

    /**
     * 更改文章状态
     */
    void updateArticleStatus(String articleId, Integer pendingStatus);


    void deleteArticle(String userId, String articleId);


   void withdrawArticle(String userId, String articleId) ;

    /**
     * 用户查询自己的文章列表
     */
    PagedGridResult queryAllList(
            Integer status,
            Integer page,
            Integer pageSize);


}
