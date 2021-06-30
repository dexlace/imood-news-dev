package com.dexlace.html.consumer;

import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/11
 */
@Component
public class ArticleHtmlProcessor {
    final static Logger logger = LoggerFactory.getLogger(ArticleHtmlProcessor.class);

    @Value("${freemarker.html.article}")
    private String articlePath;

    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;


    public Integer download(String articleId, String articleMongoId) throws Exception {
        // 拼接最终文件的保存地址
        String path = articlePath + File.separator + articleId + ".html";
        // 获取文件流，定义存放位置和名称
        File file = new File(path);
        // 创建输出流
        OutputStream os = new FileOutputStream(file);

        // 执行下载
        gridFSBucket.downloadToStream(new ObjectId(articleMongoId), os);

        os.close();

        return HttpStatus.OK.value();
    }

    public Integer delete(String articleId, String articleMongoId) throws Exception {
        gridFsTemplate.delete(Query.query(Criteria.where("filename").is(articleId + ".html")));

        // 拼接最终文件的保存地址,这是要删除的路径
        String path = articlePath + File.separator + articleId + ".html";

        logger.info("path: {}",path);
        // 获取文件流，定义删除文件的位置和名称
        File file = new File(path);

        if (!file.exists()){

            return HttpStatus.NOT_FOUND.value();

        }

        // 注意 上个download方法需要关闭流，不然这里删除不了文件
       file.delete();


        return HttpStatus.OK.value();



    }
}
