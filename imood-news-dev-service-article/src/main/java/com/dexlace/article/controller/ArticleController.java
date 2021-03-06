package com.dexlace.article.controller;

import com.dexlace.api.config.RabbitMqConfig;
import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.article.ArticleControllerApi;
import com.dexlace.article.service.ArticleService;
import com.dexlace.common.enums.ArticleCoverType;
import com.dexlace.common.enums.ArticleReviewStatus;
import com.dexlace.common.enums.YesOrNo;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.bo.NewArticleBO;
import com.dexlace.model.entity.Category;
import com.dexlace.model.eo.ArticleEO;
import com.dexlace.model.vo.ArticleDetailVO;
import com.mongodb.client.gridfs.GridFSBucket;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dexlace.api.service.BaseService.REDIS_ALL_CATEGORY;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@RestController
public class ArticleController extends BaseController implements ArticleControllerApi {

    final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Value("${freemarker.html.article}")
    private String articlePath;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private ElasticsearchTemplate esTemplate;


    /**
     * ????????????
     * @param newArticleBO
     * @param result
     * @return
     */
    @Override
    public GraceIMOODJSONResult createArticle(NewArticleBO newArticleBO, BindingResult result) {

        // ??????BindingResult?????????????????????????????????????????????????????????return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        // ?????????????????????????????????????????????????????????????????????????????????????????? else if
        if (newArticleBO.getArticleType() == ArticleCoverType.ONE_IMAGE.type) {
            if (StringUtils.isBlank(newArticleBO.getArticleCover())) {
                return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_COVER_NOT_EXIST_ERROR);
            }
        } else if (newArticleBO.getArticleType() == ArticleCoverType.WORDS.type) {
            newArticleBO.setArticleCover("");
        }

        // ????????????id????????????
        String allCategoryJson = redis.get(REDIS_ALL_CATEGORY);
        List<Category> categoryList = JsonUtils.jsonToList(allCategoryJson, Category.class);
        Category category = null;
        for (Category c : categoryList) {
            if (c.getId() == newArticleBO.getCategoryId()) {
                category = c;
                break;
            }
        }
        if (category == null) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_CATEGORY_NOT_EXIST_ERROR);
        }


        articleService.createArticle(newArticleBO, category);
        // ????????????
        System.out.println(newArticleBO.toString());


        return GraceIMOODJSONResult.ok();
    }


    @Override
    public GraceIMOODJSONResult queryMyList(String userId, String keyword,
                                            Integer status,
                                            Date startDate, Date endDate,
                                            Integer page, Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_QUERY_PARAMS_ERROR);
        }

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = articleService.queryMyArticleList(userId,
                keyword,
                status,
                startDate,
                endDate,
                page,
                pageSize);
        return GraceIMOODJSONResult.ok(gridResult);
    }

    @Override
    public GraceIMOODJSONResult queryAllList(Integer status, Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = articleService.queryAllList(status, page, pageSize);

        return GraceIMOODJSONResult.ok(gridResult);
    }


    /**
     * ????????????
     * @param articleId
     * @param passOrNot
     * @return
     */
    @Override
    public GraceIMOODJSONResult doReview(String articleId, Integer passOrNot) {

        Integer pendingStatus;
        if (passOrNot == YesOrNo.YES.type) {
            pendingStatus = ArticleReviewStatus.SUCCESS.type;
        } else if (passOrNot == YesOrNo.NO.type) {
            pendingStatus = ArticleReviewStatus.FAILED.type;
        } else {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }

        articleService.updateArticleStatus(articleId, pendingStatus);

        /**
         * ?????????????????????????????????????????????????????????
         */
        if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
            // ?????????????????????html
            try {
//                createArticleHTML(articleId);
                // ???????????????id????????????mongodb gridfs??????articleMongId???
                // articleMongId??????mongodb??????????????????
                // ????????????????????????????????????????????????????????????
                // ???service????????????????????????????????????????????????????????????
                String articleMongoId = createArticleHTMLToGridFs(articleId);
                articleService.updateArticleMongodb(articleId, articleMongoId);

//                // ??????????????????????????????????????????????????????????????????html???
//                doDownloadArticleHTML(articleId, articleMongoId);
                // ???????????????mq?????????????????????????????????????????????html
                doDownloadArticleHTMLByMQ(articleId,articleMongoId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return GraceIMOODJSONResult.ok();
    }


    private void doDownloadArticleHTML(String articleId, String articleMongoId) {
        String htmlUrl = "http://html.imoocnews.com:8010/article/html/download?articleId=" + articleId + "&articleMongoId=" + articleMongoId;
        ResponseEntity<Integer> response = restTemplate.getForEntity(htmlUrl, Integer.class);
        Integer status = response.getBody();
        if (status != HttpStatus.OK.value()) {
            GraceException.display(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private void doDownloadArticleHTMLByMQ(String articleId, String articleMongoId) {
       rabbitTemplate.convertAndSend(
               RabbitMqConfig.EXCHANGE_ARTICLE,
               "article.download",
               articleId+"-"+articleMongoId);
    }


    @Override
    public GraceIMOODJSONResult delete(String userId, String articleId) {
        String mongoFileId = articleService.deleteArticle(userId, articleId);
//        deleteArticleHTML(articleId, mongoFileId);
        esTemplate.delete(ArticleEO.class, articleId);
        deleteArticleHTMLByMQ(articleId,mongoFileId);
        return GraceIMOODJSONResult.ok();
    }


    private void deleteArticleHTML(String articleId, String articleMongoId) {
        String htmlUrl = "http://html.imoocnews.com:8010/article/html/delete?articleId=" + articleId + "&articleMongoId=" + articleMongoId;
        ResponseEntity<Integer> response = restTemplate.getForEntity(htmlUrl, Integer.class);
        Integer status = response.getBody();
        if (status != HttpStatus.OK.value()) {
            GraceException.display(ResponseStatusEnum.ARTICLE_DELETE_ERROR);
        }
    }

    private void deleteArticleHTMLByMQ(String articleId, String articleMongoId) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_ARTICLE,
                "article.delete",
                articleId+"-"+articleMongoId);
    }

    @Override
    public GraceIMOODJSONResult withdraw(String userId, String articleId) {
        String mongoFileId = articleService.withdrawArticle(userId, articleId);
//        deleteArticleHTML(articleId, mongoFileId);
        esTemplate.delete(ArticleEO.class, articleId);
        deleteArticleHTMLByMQ(articleId,mongoFileId);
        return GraceIMOODJSONResult.ok();
    }


    /**
     * // ???????????????????????????html???????????????FreemarkerController??????????????????
     * // ??????????????????????????????mongobd???gridfs
     **/
    public String createArticleHTMLToGridFs(String articleId) throws Exception {

        Configuration cfg = new Configuration(Configuration.getVersion());
        String classpath = this.getClass().getResource("/").getPath();
        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));

        Template template = cfg.getTemplate("detail.ftl", "utf-8");

        // ???????????????????????????
        // ????????????????????????
        ArticleDetailVO detailVO = getArticleDetail(articleId);
        Map<String, Object> map = new HashMap<>();
        map.put("articleDetail", detailVO);


        /**
         * ?????????html???????????????String??????
         * ????????????freemarker???????????????
         * ???????????????????????????????????????
         */
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(htmlContent);
        /**
         *
         * ?????????string????????????????????????
         */
        InputStream inputStream = IOUtils.toInputStream(htmlContent);


        /**
         * * @param filename the filename for the stream
         *      * @param source the Stream providing the file data
         *      * @return the ObjectId of the uploaded file.
         */
        ObjectId fileId = gridFSBucket.uploadFromStream(detailVO.getId() + ".html", inputStream);

        System.out.println("fileId ??????????????????  ???" + fileId);


        return fileId.toString();

    }


//    // ???????????????????????????html???????????????FreemarkerController??????????????????
//    public void createArticleHTML(String articleId) throws Exception {
//
//        Configuration cfg = new Configuration(Configuration.getVersion());
//        String classpath = this.getClass().getResource("/").getPath();
//        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));
//
//        Template template = cfg.getTemplate("detail.ftl", "utf-8");
//
//        // ???????????????????????????
//        // ????????????????????????
//        ArticleDetailVO detailVO = getArticleDetail(articleId);
//        Map<String, Object> map = new HashMap<>();
//        map.put("articleDetail", detailVO);
//
//        File tempDic = new File(articlePath);
//        if (!tempDic.exists()) {
//            tempDic.mkdirs();
//        }
//
//        String path = articlePath + File.separator + detailVO.getId() + ".html";
//
//        Writer out = new FileWriter(path);
//        template.process(map, out);
//        out.close();
//    }

    // ??????????????????rest???????????????????????????
    // ??????controller??????????????????controller?????????????????????controller???????????????????????????
    // ???????????????restTemplate??????????????????
    public ArticleDetailVO getArticleDetail(String articleId) {
        String url
                = "http://www.imoocnews.com:8001/portal/article/detail?articleId=" + articleId;
        ResponseEntity<GraceIMOODJSONResult> responseEntity
                = restTemplate.getForEntity(url, GraceIMOODJSONResult.class);
        GraceIMOODJSONResult bodyResult = responseEntity.getBody();
        ArticleDetailVO detailVO = null;
        if (bodyResult.getStatus() == 200) {
            String detailJson = JsonUtils.objectToJson(bodyResult.getData());
            detailVO = JsonUtils.jsonToPojo(detailJson, ArticleDetailVO.class);
        }
        return detailVO;
    }

}

