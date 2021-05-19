package com.dexlace.article.service;

import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.entity.Article;
import com.dexlace.model.vo.ArticleDetailVO;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
public interface ArticlePortalService {

    /**
     * 首页查询文章列表
     */
     PagedGridResult queryIndexArticleList(String keyword, Integer category, Integer page, Integer pageSize);


    /**
     * 查询作家发布的所有文章列表
     */
    PagedGridResult queryArticleListOfWriter(String writerId,
                                             Integer page,
                                             Integer pageSize);



    /**
     * 查询热闻
     */
     List<Article> queryHotArticleList();



    /**
     * 作家页面查询近期佳文
     */
     PagedGridResult queryGoodArticleListOfWriter(String writerId);


    /**
     * 文章详情
     */
     ArticleDetailVO detail(String articleId);


}

