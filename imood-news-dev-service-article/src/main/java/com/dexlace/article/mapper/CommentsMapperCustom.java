package com.dexlace.article.mapper;

import com.dexlace.model.vo.CommentsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentsMapperCustom {

    /**
     * 查询文章的评论
     */
    public List<CommentsVO> queryArticleCommentList(@Param("paramsMap") Map<String, Object> map);

}
