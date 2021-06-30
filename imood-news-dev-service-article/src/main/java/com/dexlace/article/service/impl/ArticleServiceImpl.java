package com.dexlace.article.service.impl;

import com.dexlace.api.config.RabbitMqDelayConfig;
import com.dexlace.api.service.BaseService;
import com.dexlace.article.mapper.ArticleMapper;
import com.dexlace.article.mapper.ArticleMapperCustom;
import com.dexlace.article.service.ArticleService;
import com.dexlace.common.enums.ArticleAppointType;
import com.dexlace.common.enums.ArticleReviewLevel;
import com.dexlace.common.enums.ArticleReviewStatus;
import com.dexlace.common.enums.YesOrNo;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.common.utils.extend.AliTextReviewUtils;
import com.dexlace.model.bo.NewArticleBO;
import com.dexlace.model.entity.Article;
import com.dexlace.model.entity.Category;
import com.dexlace.model.eo.ArticleEO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/13
 */
@Service
public class ArticleServiceImpl  extends BaseService implements ArticleService {

    final static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private Sid sid;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleMapperCustom articleMapperCustom;


    @Autowired
    private AliTextReviewUtils aliTextReviewUtils;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ElasticsearchTemplate esTemplate;


    @Transactional
    @Override
    public void createArticle(NewArticleBO newArticleBO, Category category) {

        String articleId = sid.nextShort();

        Article article = new Article();
        BeanUtils.copyProperties(newArticleBO, article);

        article.setId(articleId);
        article.setCategoryId(category.getId());

        article.setArticleStatus(ArticleReviewStatus.REVIEWING.type);
        article.setCommentCounts(0);
        article.setReadCounts(0);
        article.setIsDelete(YesOrNo.NO.type);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());

        // 如果是预约发布时间，则需要条填充发布时间，否则按照用户提交时间
        if (article.getIsAppoint() == ArticleAppointType.TIMING.type) {
            article.setPublishTime(newArticleBO.getPublishTime());
        } else if (article.getIsAppoint() == ArticleAppointType.IMMEDIATELY.type) {
            article.setPublishTime(new Date());
        }

        int result = articleMapper.insert(article);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_CREATE_ERROR);
        }


        // 得到发布时间，需要计算往后延迟的时间
        if (article.getIsAppoint() == ArticleAppointType.TIMING.type) {
            // 得到毫秒数
            final Long delay = newArticleBO.getPublishTime().getTime() - new Date().getTime();



            MessagePostProcessor messagePostProcessor= message -> {
                message.getMessageProperties()
                        // 设置消息持久化
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                // 设置发布的时间延时
                message.getMessageProperties().setDelay(delay.intValue());
                return message;
            };


            rabbitTemplate.convertAndSend(
                    RabbitMqDelayConfig.EXCHANGE_DELAY,
                    "article.delay.publish",
                    articleId,
                    messagePostProcessor
                    );


        }


        /**
         *
         */
        // 阿里智能AI进行文本自动检测
//        String reviewResult = aliTextReviewUtils.reviewTextContent(newArticleBO.getTitle() + newArticleBO.getContent());
        // 写死，需要人工审核
        String reviewResult = ArticleReviewLevel.REVIEW.type;
        logger.info("检测结果：" + reviewResult);

        if (ArticleReviewLevel.PASS.type.equalsIgnoreCase(reviewResult)) {
            logger.info("审核通过");
            // 修改文章状态为审核通过
            this.updateArticleStatus(articleId, ArticleReviewStatus.SUCCESS.type);
        } else if (ArticleReviewLevel.REVIEW.type.equalsIgnoreCase(reviewResult)) {
            logger.info("需要人工复审");
            // 修改文章状态为需要人工复审
            this.updateArticleStatus(articleId, ArticleReviewStatus.WAITING_MANUAL.type);
        } else if (ArticleReviewLevel.BLOCK.type.equalsIgnoreCase(reviewResult)) {
            logger.info("审核不通过");
            // 修改文章状态为审核不通过
            this.updateArticleStatus(articleId, ArticleReviewStatus.FAILED.type);
        }

    }



    @Transactional
    @Override
    public void updateArticleStatus(String articleId, Integer pendingStatus) {
        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = articleExample.createCriteria();
        criteria.andEqualTo("id", articleId);

        Article pending = new Article();
        pending.setArticleStatus(pendingStatus);
        int result = articleMapper.updateByExampleSelective(pending, articleExample);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }

        // 如果审核通过，则查询article，存入es中
        if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
            Article article= articleMapper.selectByPrimaryKey(articleId);
            // 如果是即时发布的文章，审核通过后则可以直接存入es中
            if(article.getIsAppoint() == ArticleAppointType.IMMEDIATELY.type) {
                ArticleEO articleEO = new ArticleEO();
                articleEO.setId(articleId);
                articleEO.setTitle(article.getTitle());
                articleEO.setCategoryId(article.getCategoryId());
                articleEO.setArticleType(article.getArticleType());
                articleEO.setArticleCover(article.getArticleCover());
                articleEO.setPublishTime(article.getPublishTime());
                articleEO.setPublishUserId(article.getPublishUserId());
                IndexQuery iq = new IndexQueryBuilder().withObject(articleEO).build();
                esTemplate.index(iq);
            }
            // FIXME 如果是定时发布的文章，此处不能放入es，需要在定时的延迟队列中执行,
            // 需要在审核通过的情况下  在定时发布的时间放入es  略

        }


    }

    @Transactional
    @Override
    public void updateArticleMongodb(String articleId, String mongoId) {

        Article pendingArticle = new Article();
        pendingArticle.setId(articleId);
        pendingArticle.setMongoFileId( mongoId);
        articleMapper.updateByPrimaryKeySelective(pendingArticle);

    }


    @Transactional
    @Override
    public void updateAppointToPublish() {
        articleMapperCustom.updateAppointToPublish();
    }



    @Transactional
    @Override
    public void updateAppointToPublishRabbitmq(String articleId) {

        Article article=new Article();
        article.setId(articleId);
        article.setIsAppoint(ArticleAppointType.IMMEDIATELY.type);
        articleMapper.updateByPrimaryKeySelective(article);
    }


    @Override
    public PagedGridResult queryMyArticleList(String userId, String keyword,
                                              Integer status,
                                              Date startDate, Date endDate,
                                              Integer page, Integer pageSize) {

        Example articleExample = new Example(Article.class);
        articleExample.orderBy("createTime").desc();
        Example.Criteria criteria = articleExample.createCriteria();
        criteria.andEqualTo("publishUserId", userId);

        if (StringUtils.isNotBlank(keyword)) {
            criteria.andLike("title", "%" + keyword + "%");
        }

        // 传入的状态是否是有效值
        if (ArticleReviewStatus.isArticleStatusValid(status)) {
            criteria.andEqualTo("articleStatus", status);
        }

        // 审核中是机审和人审核的两个状态，所以需要单独判断
        // 针对12的组合，因为机审和人审的问题，与上面校验status不会同时执行
        if (status != null && status == 12) {
            // 机审和人审都是审核
            criteria.andEqualTo("articleStatus", ArticleReviewStatus.REVIEWING.type)
                    .orEqualTo("articleStatus", ArticleReviewStatus.WAITING_MANUAL.type);
        }


        //isDelete 必须是0
        criteria.andEqualTo("isDelete", YesOrNo.NO.type);

        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("publishTime", startDate);
        }
        if (endDate != null) {
            criteria.andLessThanOrEqualTo("publishTime", endDate);
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
    public PagedGridResult queryAllList(Integer status, Integer page, Integer pageSize) {
        Example articleExample = new Example(Article.class);
        articleExample.orderBy("createTime").desc();
        Example.Criteria criteria = articleExample.createCriteria();

        // 传入的状态是否是有效值
        if (ArticleReviewStatus.isArticleStatusValid(status)) {
            criteria.andEqualTo("articleStatus", status);
        }


        //isDelete 必须是0
        criteria.andEqualTo("isDelete", YesOrNo.NO.type);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<Article> list = articleMapper.selectByExample(articleExample);
        return setterPagedGrid(list, page);
    }


    @Transactional
    @Override
    public String deleteArticle(String userId, String articleId) {

        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = articleExample.createCriteria();
        criteria.andEqualTo("publishUserId", userId);
        criteria.andEqualTo("id",articleId);

        Article pending = new Article();
        pending.setIsDelete(YesOrNo.YES.type);

        int result = articleMapper.updateByExampleSelective(pending, articleExample);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_DELETE_ERROR);
            return null;
        }
        return articleMapper.selectOneByExample(articleExample).getMongoFileId();

    }



    @Transactional
    @Override
    public String withdrawArticle(String userId, String articleId) {

        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = articleExample.createCriteria();
        criteria.andEqualTo("publishUserId", userId);
        criteria.andEqualTo("id",articleId);

        Article pending = new Article();
        pending.setArticleStatus(ArticleReviewStatus.WITHDRAW.type);

        int result = articleMapper.updateByExampleSelective(pending, articleExample);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_DELETE_ERROR);
            return null;
        }

        return articleMapper.selectOneByExample(articleExample).getMongoFileId();

    }
}

