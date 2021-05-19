package com.dexlace.article.mapper;


import com.dexlace.api.mymapper.MyMapper;
import com.dexlace.model.entity.Article;
import org.springframework.stereotype.Repository;
@Repository
public interface ArticleMapperCustom extends MyMapper<Article> {


        public void updateAppointToPublish();


}