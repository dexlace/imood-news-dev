package com.dexlace.article.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/5
 */
@Configuration
public class GridFSWebConfig {

    @Value("${spring.data.mongodb.database}")
    private String mongodbDatabase;


    @Value("${spring.data.mongodb.uri}")
    private String connectString;




    @Bean
    public MongoClient getMongoClient(){

        return MongoClients.create(connectString);
    }


    @Bean
    public GridFSBucket getGridFSBucket(){
        MongoDatabase database = getMongoClient().getDatabase(mongodbDatabase);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }

}

