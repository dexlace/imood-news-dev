package com.dexlace.article.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.article.ArticlePortalControllerApi;
import com.dexlace.article.service.ArticlePortalService;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.entity.Article;
import com.dexlace.model.vo.AppUserVO;
import com.dexlace.model.vo.ArticleDetailVO;
import com.dexlace.model.vo.IndexArticleVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@RestController
public class ArticlePortalController extends BaseController implements ArticlePortalControllerApi {

    final static Logger logger = LoggerFactory.getLogger(ArticlePortalController.class);

    @Autowired
    private ArticlePortalService articlePortalService;


    @Override
    public GraceIMOODJSONResult list(String keyword, Integer category, Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = articlePortalService.queryIndexArticleList(keyword, category, page, pageSize);


        return GraceIMOODJSONResult.ok(rebuildArticleGrid(gridResult));
    }


    @Override
    public GraceIMOODJSONResult hotList() {
        List<Article> hotList = articlePortalService.queryHotArticleList();
        return GraceIMOODJSONResult.ok(hotList);
    }


    // 这里还要显示作者的信息，和list其实是一样的，这里显示的内容也和list一样
    @Override
    public GraceIMOODJSONResult queryArticleListOfWriter(String writerId, Integer page, Integer pageSize) {


        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = articlePortalService.queryArticleListOfWriter(writerId, page, pageSize);

        return GraceIMOODJSONResult.ok(rebuildArticleGrid(gridResult));
    }

    @Override
    public GraceIMOODJSONResult queryGoodArticleListOfWriter(String writerId) {
        PagedGridResult gridResult = articlePortalService.queryGoodArticleListOfWriter(writerId);

        return GraceIMOODJSONResult.ok(gridResult);
    }


    private PagedGridResult rebuildArticleGrid(PagedGridResult gridResult) {

        // START
        /**
         * FIXME:
         * 并发查询的时候要减少多表关联查询，尤其首页的文章列表。
         * 其次，微服务有边界，不同系统各自需要查询各自的表数据
         * 在这里采用单表查询文章以及用户，然后再业务层（controller或service）拼接，
         * 而且，文章服务和用户服务是分开的，所以持久层的查询也是在不同的系统进行调用的。
         * 对于用户来说是无感知的，这也是比较好的一种方式。
         * 此外，后续结合elasticsearch扩展也是通过业务层拼接方式来做。
         */

        List<Article> articleList = (List<Article>) gridResult.getRows();
        // 1. 构建用户id列表
        Set<String> idSet = new HashSet<>();

        // 得到文章阅读数列表的键值
        List<String> readList = new ArrayList<>();
        for (Article a : articleList) {
            idSet.add(a.getPublishUserId());
            readList.add(REDIS_ARTICLE_READ_COUNTS + ":" + a.getId());
        }

        // 每一篇的文章阅读数都对应上了，存入这个键值列表了
        List<String> readCountsRedis = redis.mget( readList);


        //  2. 发起restTemplate请求查询用户列表
        List<AppUserVO> publishUserList = remoteCalBasicUserInfo(idSet);

        if (null != publishUserList) {

            // 3. 重组文章列表
            List<IndexArticleVO> indexArticleList = new ArrayList<>();
//            for (Article a : articleList) {
//                IndexArticleVO indexArticleVO = new IndexArticleVO();
//                BeanUtils.copyProperties(a, indexArticleVO);
//                // 3.1 从userList中获得publisher基本信息
//                for (AppUserVO appUserVO : publishUserList) {
//                    if (a.getPublishUserId().equalsIgnoreCase(appUserVO.getId())) {
//                        // 3.2 把文章放入新的文章list中
//                        indexArticleVO.setPublisherVO(appUserVO);
//                    }
//                }
//
//                // 每次都发起一个读取单个key的请求
////                int readCounts = getCountsFromRedis(REDIS_ARTICLE_READ_COUNTS + ":" + a.getId());
////                indexArticleVO.setReadCounts(readCounts);
//
//                indexArticleList.add(indexArticleVO);
//            }

            for (int i = 0; i < articleList.size(); i++) {
                IndexArticleVO indexArticleVO = new IndexArticleVO();
                Article a = articleList.get(i);
                BeanUtils.copyProperties(a, indexArticleVO);

                // 3.1 从publisherList中获得发布者的基本信息
                AppUserVO publisher = getUserIfPublisher(a.getPublishUserId(), publishUserList);
                indexArticleVO.setPublisherVO(publisher);

                // 3.2 组装文章阅读量
                String readCountsStr = readCountsRedis.get(i);
                int readCounts = 0;
                if (StringUtils.isNotBlank(readCountsStr)) {
                    readCounts = Integer.parseInt(readCountsStr);
                }
                indexArticleVO.setReadCounts(readCounts);


                indexArticleList.add(indexArticleVO);
            }


            // 重新设置rows
            gridResult.setRows(indexArticleList);
        }
        return gridResult;
    }


    @Override
    public GraceIMOODJSONResult detail(String articleId) {

        // 查询文章内容
        ArticleDetailVO article = articlePortalService.detail(articleId);


        // 现在要通过用户的id寻找用户的基本信息再set到VO
        List<AppUserVO> publishUsers = null;

        Set<String> publishUserIdSet = new HashSet<>();
        publishUserIdSet.add(article.getPublishUserId());

        publishUsers = remoteCalBasicUserInfo(publishUserIdSet);

        if (!publishUsers.isEmpty()) {
            article.setPublishUserName(publishUsers.get(0).getNickname());
        }

        article.setReadCounts(
                getCountsFromRedis(
                        REDIS_ARTICLE_READ_COUNTS + ":" + articleId));


        return GraceIMOODJSONResult.ok(article);


    }


    @Override
    public GraceIMOODJSONResult readArticle(String articleId, HttpServletRequest request) {


        String userIP = IPUtil.getRequestIp(request);
        // 设置永久存在key，表示该ip已经阅读过了，无法累加阅读量
        redis.setnx(ARTICLE_ALREADY_READ + ":" + articleId + ":" + userIP, userIP);

        // redis 文章阅读数累加
        redis.increment(REDIS_ARTICLE_READ_COUNTS + ":" + articleId, 1);

        return GraceIMOODJSONResult.ok();
    }


    public AppUserVO getUserIfPublisher(String publishUserId, List<AppUserVO> publishUserList) {

        for (AppUserVO appUserVO : publishUserList) {
            if (publishUserId.equalsIgnoreCase(appUserVO.getId())) {
                return appUserVO;
            }
        }
        return null;

    }

}


