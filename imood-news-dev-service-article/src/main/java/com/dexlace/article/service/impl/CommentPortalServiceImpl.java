package com.dexlace.article.service.impl;

import com.dexlace.api.service.BaseService;
import com.dexlace.article.mapper.CommentsMapper;
import com.dexlace.article.mapper.CommentsMapperCustom;
import com.dexlace.article.service.ArticlePortalService;
import com.dexlace.article.service.CommentPortalService;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.entity.Comments;
import com.dexlace.model.vo.ArticleDetailVO;
import com.dexlace.model.vo.CommentsVO;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/16
 */
@Service
public class CommentPortalServiceImpl extends BaseService implements CommentPortalService {
    @Autowired
    private Sid sid;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CommentsMapperCustom commentsMapperCustom;

    @Autowired
    private ArticlePortalService articlePortalService;

    @Transactional
    @Override
    public void createComment(String articleId, String fatherCommentId, String content, String userId, String nickname,String face) {

        // article由id查出  并设置冗余的评论信息
        ArticleDetailVO article = articlePortalService.detail(articleId);

        String commentId = sid.nextShort();

        Comments newComments = new Comments();
        newComments.setId(commentId);

        newComments.setFatherId(fatherCommentId);
        newComments.setCommentUserId(userId);
        newComments.setCommentUserNickname(nickname);
        newComments.setContent(content);
        newComments.setCreateTime(new Date());


        // 冗余的article信息
        newComments.setWriterId(article.getPublishUserId());
        newComments.setArticleId(articleId);
        newComments.setArticleTitle(article.getTitle());
        newComments.setArticleCover(article.getCover());
        newComments.setCommentUserFace(face);


        // 评论数+1
        // 评论数累加
        redis.increment(REDIS_ARTICLE_COMMENT_COUNTS + ":" + articleId, 1);


        commentsMapper.insert(newComments);

    }


    @Override
    public PagedGridResult queryArticleComments(String articleId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);

        PageHelper.startPage(page, pageSize);
        List<CommentsVO> list = commentsMapperCustom.queryArticleCommentList(map);
        return setterPagedGrid(list, page);
    }


    @Override
    public PagedGridResult queryWriterCommentsMng(String writerId, Integer page, Integer pageSize) {

        Comments comment = new Comments();
        comment.setWriterId(writerId);

        PageHelper.startPage(page, pageSize);
        List<Comments> list = commentsMapper.select(comment);
        return setterPagedGrid(list, page);
    }

    @Override
    public void deleteComment(String writerId, String commentId) {
        Comments comment = new Comments();
        comment.setId(commentId);
        comment.setWriterId(writerId);

        commentsMapper.delete(comment);
    }

}

