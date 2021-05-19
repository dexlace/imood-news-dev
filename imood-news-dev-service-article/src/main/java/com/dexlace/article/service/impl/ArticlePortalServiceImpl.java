package com.dexlace.article.service.impl;

import com.dexlace.api.service.BaseService;
import com.dexlace.article.mapper.ArticleMapper;
import com.dexlace.article.mapper.ArticleMapperCustom;
import com.dexlace.article.service.ArticlePortalService;
import com.dexlace.article.service.ArticleService;
import com.dexlace.common.enums.ArticleReviewLevel;
import com.dexlace.common.enums.ArticleReviewStatus;
import com.dexlace.common.enums.YesOrNo;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.common.utils.extend.AliTextReviewUtils;
import com.dexlace.model.bo.NewArticleBO;
import com.dexlace.model.entity.Article;
import com.dexlace.model.entity.Category;
import com.dexlace.model.vo.AppUserVO;
import com.dexlace.model.vo.ArticleDetailVO;
import com.dexlace.model.vo.IndexArticleVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@Service
public class ArticlePortalServiceImpl extends BaseService implements ArticlePortalService {

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public PagedGridResult queryIndexArticleList(String keyword, Integer category, Integer page, Integer pageSize) {
        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = makeExample(articleExample);

        /**
         * 自带隐性查询条件：
         * isPoint为即时发布，表示文章已经直接发布，或者定时任务到点发布
         * isDelete为未删除，表示文章不能展示已经被删除的
         * status为审核通过，表示文章经过机审/人审通过
         */


        // category 为空则查询全部，不指定分类
        // keyword 为空则查询全部
        if (StringUtils.isNotBlank(keyword)) {
            criteria.andLike("title", "%" + keyword + "%");
        }
        if (category != null) {
            criteria.andEqualTo("categoryId", category);
        }


        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<Article> list = articleMapper.selectByExample(articleExample);
        return setterPagedGrid(list, page);
    }


    @Override
    public List<Article> queryHotArticleList() {
        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = makeExample(articleExample);


        PageHelper.startPage(1, 5);
        List<Article> list = articleMapper.selectByExample(articleExample);
        return list;
    }

    @Override
    public PagedGridResult queryArticleListOfWriter(String writerId, Integer page, Integer pageSize) {

        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = makeExample(articleExample);
        criteria.andEqualTo("publishUserId", writerId);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<Article> list = articleMapper.selectByExample(articleExample);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult queryGoodArticleListOfWriter(String writerId) {
        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = makeExample(articleExample);
        criteria.andEqualTo("publishUserId", writerId);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(1, 5);
        List<Article> list = articleMapper.selectByExample(articleExample);
        return setterPagedGrid(list, 1);
    }


    private Example.Criteria makeExample(Example articleExample) {

        /**
         * 自带隐性查询条件：
         * isPoint为即时发布，表示文章已经直接发布，或者定时任务到点发布
         * isDelete为未删除，表示文章不能展示已经被删除的
         * status为审核通过，表示文章经过机审/人审通过
         */

        articleExample.orderBy("publishTime").desc();
        Example.Criteria criteria = articleExample.createCriteria();

        criteria.andEqualTo("isAppoint", YesOrNo.NO.type);
        criteria.andEqualTo("isDelete", YesOrNo.NO.type);
        criteria.andEqualTo("articleStatus", ArticleReviewStatus.SUCCESS.type);

        return criteria;
    }


    @Override
    public ArticleDetailVO detail(String articleId) {
        Article article = new Article();
        article.setId(articleId);
        article.setIsDelete(YesOrNo.NO.type);
        article.setIsAppoint(YesOrNo.NO.type);
        article.setArticleStatus(ArticleReviewStatus.SUCCESS.type);

        // article会得到发布者的用户id
        article = articleMapper.selectOne(article);

        ArticleDetailVO detailVO = new ArticleDetailVO();
        BeanUtils.copyProperties(article, detailVO);
        detailVO.setCover(article.getArticleCover());

        return detailVO;
    }





}

