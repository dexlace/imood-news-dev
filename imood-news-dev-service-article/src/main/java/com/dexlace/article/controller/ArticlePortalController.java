package com.dexlace.article.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.article.ArticlePortalControllerApi;
import com.dexlace.article.service.ArticlePortalService;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.entity.Article;
import com.dexlace.model.eo.ArticleEO;
import com.dexlace.model.vo.AppUserVO;
import com.dexlace.model.vo.ArticleDetailVO;
import com.dexlace.model.vo.IndexArticleVO;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private DiscoveryClient discoveryClient;


    @Autowired
    private ElasticsearchTemplate esTemplate;



    /**
     * ?????????????????????
     * @param publishUserIdSet
     * @return
     */
    @Override
    public List<AppUserVO> remoteCallBasicUserInfo(Set publishUserIdSet) {


        String serviceId = "SERVICE-USER";
        List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);
        ServiceInstance userService = instanceList.get(0);

        for(Object id:publishUserIdSet){
            System.out.println(id.toString());

        }
        System.out.println(JsonUtils.objectToJson(publishUserIdSet));

        String userServerUrlExecute
                = "http://" + userService.getHost() + ":" + userService.getPort() + "/user/queryByIds?userIds=" + JsonUtils.objectToJson(publishUserIdSet);

        ResponseEntity<GraceIMOODJSONResult> responseEntity
                = restTemplate.getForEntity(userServerUrlExecute, GraceIMOODJSONResult.class);
        GraceIMOODJSONResult bodyResult = responseEntity.getBody();
        List<AppUserVO> publishUserList = null;
        if (bodyResult.getStatus() == 200) {
            String userJson = JsonUtils.objectToJson(bodyResult.getData());
            publishUserList = JsonUtils.jsonToList(userJson, AppUserVO.class);
        }
        return publishUserList;

    }




    @Override
    public GraceIMOODJSONResult eslist(String keyword,
                                  Integer category,
                                  Integer page,
                                  Integer pageSize) {

        /**
         * es?????????
         *      1. ?????????????????????????????????
         *      2. ????????????????????????
         *      3. ?????????????????????
         */

        // es???????????????0?????????????????????????????????page??????-1
        if (page<1) return null;
        page--;
        Pageable pageable = PageRequest.of(page, pageSize);

        SearchQuery query = null;
        AggregatedPage<ArticleEO> pagedArticle =null;
        // ?????????1?????????
        if (StringUtils.isBlank(keyword) && category == null) {
            // query??????
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchAllQuery())
                    .withPageable(pageable)
                    .build();
            // ??????
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
        }

        // ?????????2?????????
        if (StringUtils.isBlank(keyword) && category != null) {
            // query??????
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.termQuery("categoryId", category))
                    .withPageable(pageable)
                    .build();
            // ??????
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
        }

//        // ?????????3?????????
//        if (StringUtils.isNotBlank(keyword) && category == null) {
//            query = new NativeSearchQueryBuilder()
//                    .withQuery(QueryBuilders.matchQuery("title", keyword))
//                    .withPageable(pageable)
//                    .build();
//        }



        if (StringUtils.isNotBlank(keyword) && category == null) {
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("title", keyword))
                    .withPageable(pageable)
                    .build();
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
        }

        // ?????????3????????? ??????
        String searchTitleFiled = "title";
        if (StringUtils.isNotBlank(keyword) && category == null) {
            String preTag = "<font color='red'>";
            String postTag = "</font>";
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery(searchTitleFiled, keyword))  // ????????????????????????????????????????????????
                    .withHighlightFields(new HighlightBuilder.Field(searchTitleFiled)
                            .preTags(preTag)
                            .postTags(postTag))
                    .withPageable(pageable)
                    .build();


            // ?????????es??????????????????
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class, new SearchResultMapper() {
                // ????????????????????????
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                    List<ArticleEO> articleHighLightList = new ArrayList<>();
                    // ????????????????????????
                    SearchHits hits = searchResponse.getHits();
                    for (SearchHit h : hits) {
                        HighlightField highlightField = h.getHighlightFields().get(searchTitleFiled);
                        String title = highlightField.getFragments()[0].toString();

                        // ????????????????????????????????????
                        String articleId = (String)h.getSourceAsMap().get("id");
                        Integer categoryId = (Integer)h.getSourceAsMap().get("categoryId");
                        Integer articleType = (Integer)h.getSourceAsMap().get("articleType");
                        String articleCover = (String)h.getSourceAsMap().get("articleCover");
                        String publishUserId = (String)h.getSourceAsMap().get("publishUserId");
                        Long dateLong = (Long)h.getSourceAsMap().get("publishTime");
                        Date publishTime = new Date(dateLong);

                        ArticleEO articleEO = new ArticleEO();
                        articleEO.setId(articleId);
                        articleEO.setTitle(title);
                        articleEO.setCategoryId(categoryId);
                        articleEO.setArticleType(articleType);
                        articleEO.setArticleCover(articleCover);
                        articleEO.setPublishUserId(publishUserId);
                        articleEO.setPublishTime(publishTime);

                        articleHighLightList.add(articleEO);
                    }

                    return new AggregatedPageImpl<>((List<T>)articleHighLightList,
                            pageable,
                            searchResponse.getHits().totalHits);
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }
            });


        }

        // ??????????????????
        List<ArticleEO> articleEOList = pagedArticle.getContent();
        List<Article> articleList = new ArrayList<>();
        for (ArticleEO a : articleEOList) {
            Article article = new Article();
            System.out.println(a.getPublishUserId());
            BeanUtils.copyProperties(a, article);
            articleList.add(article);
        }

        // ????????????????????????grid??????
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(articleList);
        gridResult.setPage(page + 1);
        gridResult.setTotal(pagedArticle.getTotalPages());
        gridResult.setRecords(pagedArticle.getTotalElements());

        gridResult = rebuildArticleGrid(gridResult);


        return GraceIMOODJSONResult.ok(gridResult);
    }









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


    // ???????????????????????????????????????list????????????????????????????????????????????????list??????
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
         * ?????????????????????????????????????????????????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????????????????controller???service????????????
         * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * ????????????????????????????????????????????????????????????????????????
         * ?????????????????????elasticsearch????????????????????????????????????????????????
         */

        List<Article> articleList = (List<Article>) gridResult.getRows();
        // 1. ????????????id??????
        Set<String> idSet = new HashSet<>();

        // ????????????????????????????????????
        List<String> readList = new ArrayList<>();
        for (Article a : articleList) {
            System.out.println(a.getPublishUserId());
            idSet.add(a.getPublishUserId());

            readList.add(REDIS_ARTICLE_READ_COUNTS + ":" + a.getId());
        }

        // ????????????????????????????????????????????????????????????????????????
        List<String> readCountsRedis = redis.mget( readList);


        //  2. ??????restTemplate????????????????????????
        List<AppUserVO> publishUserList = remoteCallBasicUserInfo(idSet);

        if (null != publishUserList) {

            // 3. ??????????????????
            List<IndexArticleVO> indexArticleList = new ArrayList<>();
//            for (Article a : articleList) {
//                IndexArticleVO indexArticleVO = new IndexArticleVO();
//                BeanUtils.copyProperties(a, indexArticleVO);
//                // 3.1 ???userList?????????publisher????????????
//                for (AppUserVO appUserVO : publishUserList) {
//                    if (a.getPublishUserId().equalsIgnoreCase(appUserVO.getId())) {
//                        // 3.2 ???????????????????????????list???
//                        indexArticleVO.setPublisherVO(appUserVO);
//                    }
//                }
//
//                // ?????????????????????????????????key?????????
////                int readCounts = getCountsFromRedis(REDIS_ARTICLE_READ_COUNTS + ":" + a.getId());
////                indexArticleVO.setReadCounts(readCounts);
//
//                indexArticleList.add(indexArticleVO);
//            }

            for (int i = 0; i < articleList.size(); i++) {
                IndexArticleVO indexArticleVO = new IndexArticleVO();
                Article a = articleList.get(i);
                BeanUtils.copyProperties(a, indexArticleVO);

                // 3.1 ???publisherList?????????????????????????????????
                AppUserVO publisher = getUserIfPublisher(a.getPublishUserId(), publishUserList);
                indexArticleVO.setPublisherVO(publisher);

                // 3.2 ?????????????????????
                String readCountsStr = readCountsRedis.get(i);
                int readCounts = 0;
                if (StringUtils.isNotBlank(readCountsStr)) {
                    readCounts = Integer.parseInt(readCountsStr);
                }
                indexArticleVO.setReadCounts(readCounts);


                indexArticleList.add(indexArticleVO);
            }


            // ????????????rows
            gridResult.setRows(indexArticleList);
        }
        return gridResult;
    }


    @Override
    public GraceIMOODJSONResult detail(String articleId) {

        // ??????????????????
        ArticleDetailVO article = articlePortalService.detail(articleId);


        // ????????????????????????id??????????????????????????????set???VO
        List<AppUserVO> publishUsers = null;

        Set<String> publishUserIdSet = new HashSet<>();
        publishUserIdSet.add(article.getPublishUserId());

        publishUsers = remoteCallBasicUserInfo(publishUserIdSet);

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
        // ??????????????????key????????????ip??????????????????????????????????????????
        redis.setnx(ARTICLE_ALREADY_READ + ":" + articleId + ":" + userIP, userIP);

        // redis ?????????????????????
        redis.increment(REDIS_ARTICLE_READ_COUNTS + ":" + articleId, 1);

        return GraceIMOODJSONResult.ok();
    }


    @Override
    public Integer readCounts(String articleId) {

        logger.info("???????????????????????????  ???");

        return getCountsFromRedis(REDIS_ARTICLE_READ_COUNTS + ":" + articleId);
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


