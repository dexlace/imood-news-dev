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
     * 发布文章
     * @param newArticleBO
     * @param result
     * @return
     */
    @Override
    public GraceIMOODJSONResult createArticle(NewArticleBO newArticleBO, BindingResult result) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        // 判断文章封面图类型，单图必填，纯文字设置为空，考虑后续扩展用 else if
        if (newArticleBO.getArticleType() == ArticleCoverType.ONE_IMAGE.type) {
            if (StringUtils.isBlank(newArticleBO.getArticleCover())) {
                return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_COVER_NOT_EXIST_ERROR);
            }
        } else if (newArticleBO.getArticleType() == ArticleCoverType.WORDS.type) {
            newArticleBO.setArticleCover("");
        }

        // 判断分类id是否存在
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
        // 测试输出
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
     * 审核文章
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
         * 审核完毕并通过就应该文章详情页面静态化
         */
        if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
            // 审核成功，生成html
            try {
//                createArticleHTML(articleId);
                // 关联文章的id到存储到mongodb gridfs中的articleMongId，
                // articleMongId是到mongodb中的查找依据
                // 这里要做好映射关系的保存，是一个更新操作
                // 在service层里写好对应的关联关系保存的更新操作即可
                String articleMongoId = createArticleHTMLToGridFs(articleId);
                articleService.updateArticleMongodb(articleId, articleMongoId);

//                // 调用消费端（前端服务器所部署的后端程序来下载html）
//                doDownloadArticleHTML(articleId, articleMongoId);
                // 发送消息到mq队列，让消费者监听并且执行下载html
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
     * // 文章详情页生成静态html，也就是入FreemarkerController中的示例所示
     * // 为了解耦，这里上传到mongobd的gridfs
     **/
    public String createArticleHTMLToGridFs(String articleId) throws Exception {

        Configuration cfg = new Configuration(Configuration.getVersion());
        String classpath = this.getClass().getResource("/").getPath();
        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));

        Template template = cfg.getTemplate("detail.ftl", "utf-8");

        // 获得文章的详情数据
        // 这里需要远程调用
        ArticleDetailVO detailVO = getArticleDetail(articleId);
        Map<String, Object> map = new HashMap<>();
        map.put("articleDetail", detailVO);


        /**
         * 将一个html模板转换成String类型
         * 传入一个freemarker的模板对象
         * 和要传入模板对象的动态数据
         */
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(htmlContent);
        /**
         *
         * 将一个string内容转换成输入流
         */
        InputStream inputStream = IOUtils.toInputStream(htmlContent);


        /**
         * * @param filename the filename for the stream
         *      * @param source the Stream providing the file data
         *      * @return the ObjectId of the uploaded file.
         */
        ObjectId fileId = gridFSBucket.uploadFromStream(detailVO.getId() + ".html", inputStream);

        System.out.println("fileId 生成了没有啊  操" + fileId);


        return fileId.toString();

    }


//    // 文章详情页生成静态html，也就是入FreemarkerController中的示例所示
//    public void createArticleHTML(String articleId) throws Exception {
//
//        Configuration cfg = new Configuration(Configuration.getVersion());
//        String classpath = this.getClass().getResource("/").getPath();
//        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));
//
//        Template template = cfg.getTemplate("detail.ftl", "utf-8");
//
//        // 获得文章的详情数据
//        // 这里需要远程调用
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

    // 发起远程调用rest，获得文章详情数据
    // 一个controller调用另外一个controller，虽然在同一个controller，是一个重定向可以
    // 此外只能用restTemplate进行远程调用
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

