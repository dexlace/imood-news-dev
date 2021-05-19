package com.dexlace.article.service;

import com.dexlace.common.utils.PagedGridResult;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/16
 */
public interface CommentPortalService {

    /**
     * 发表评论
     */
    void createComment(String articleId,
                       String fatherCommentId,
                       String content,
                       String userId,
                       String nickname,
                       String face);


    /**
     * 查询文章的所有评论
     */
    PagedGridResult queryArticleComments(String articleId,
                                         Integer page,
                                         Integer pageSize);

    /**
     * 查询我的评论管理列表
     */
    PagedGridResult queryWriterCommentsMng(String writerId, Integer page, Integer pageSize);

    /**
     * 删除评论
     */
    void deleteComment(String writerId, String commentId);


}

