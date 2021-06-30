package com.dexlace.search.controller;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.search.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/13
 */
@RestController
public class HelloController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("createIndex")
    public Object createIndex(){


        elasticsearchTemplate.createIndex(Stu.class);
        return GraceIMOODJSONResult.ok();
    }

    @GetMapping("deleteIndex")
    public Object deleteIndex(){


        elasticsearchTemplate.deleteIndex(Stu.class);
        return GraceIMOODJSONResult.ok();
    }


    @GetMapping("addDoc")
    public Object addDoc() {
        Stu stu = new Stu();
        stu.setStuId(1001L);
        stu.setName("imood");
        stu.setAge(21);
        stu.setDesc("这世界很荒唐");
        stu.setMoney(100.2f);

        IndexQuery iq = new IndexQueryBuilder().withObject(stu).build();
        elasticsearchTemplate.index(iq);
        return GraceIMOODJSONResult.ok();
    }


    @GetMapping("updateDoc")
    public Object updateDoc() {

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("desc", "hello world");
        updateMap.put("age", 12);

        IndexRequest ir = new IndexRequest();
        ir.source(updateMap);

        UpdateQuery uq = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1001")
                .withIndexRequest(ir)
                .build();

        elasticsearchTemplate.update(uq);
        return GraceIMOODJSONResult.ok();
    }

    @GetMapping("getDoc")
    public Object getDoc(String id) {
        GetQuery gq = new GetQuery();
        gq.setId(id);
        Stu stu = elasticsearchTemplate.queryForObject(gq, Stu.class);
        return GraceIMOODJSONResult.ok(stu);
    }

    @GetMapping("detDoc")
    public Object detDoc(String id) {
        elasticsearchTemplate.delete(Stu.class, id);
        return GraceIMOODJSONResult.ok();
    }
}
