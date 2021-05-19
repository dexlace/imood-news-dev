package com.dexlace.article.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.article.ArticleControllerApi;
import com.dexlace.article.service.ArticleService;
import com.dexlace.common.enums.ArticleCoverType;
import com.dexlace.common.enums.ArticleReviewStatus;
import com.dexlace.common.enums.YesOrNo;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.bo.NewArticleBO;
import com.dexlace.model.entity.Category;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @Autowired
    private ArticleService articleService;

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
        for (Category c: categoryList) {
            if (c.getId() == newArticleBO.getCategoryId()) {
                category = c;
                break;
            }
        }
        if (category == null) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_CATEGORY_NOT_EXIST_ERROR);
        }


        articleService.createArticle(newArticleBO,category);
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
        return GraceIMOODJSONResult.ok();
    }


    @Override
    public GraceIMOODJSONResult delete(String userId, String articleId) {
        articleService.deleteArticle(userId, articleId);
        return GraceIMOODJSONResult.ok();
    }

    @Override
    public GraceIMOODJSONResult withdraw(String userId, String articleId) {
        articleService.withdrawArticle(userId, articleId);
        return GraceIMOODJSONResult.ok();
    }
}

