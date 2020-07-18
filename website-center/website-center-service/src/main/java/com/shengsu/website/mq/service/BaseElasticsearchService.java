package com.shengsu.website.mq.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-16 11:49
 **/
@Slf4j
public abstract class BaseElasticsearchService {

    /**
    * @Description: 创建索引和数据
    * @Param: * @Param elasticsearchTemplate:
 * @Param indexObject:
    * @Return: * @return: void
    * @date:
    */
    protected abstract ElasticsearchTemplate getTemplate();

    public void index(Object indexObject){
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(indexObject).build();
        getTemplate().index(indexQuery);
    }
    /**
    * @Description: 更新
    * @Param: * @Param source: 
 * @Param id: 
    * @Return: * @return: void
    * @date: 
    */
    public void update(Map<String,Object> source, String id,Class clazz){
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(source);
        UpdateQuery query = new UpdateQueryBuilder().withClass(clazz)
                .withId(id)
                .withIndexRequest(indexRequest)
                .build();
        getTemplate().update(query);
    }
    /**
    * @Description: 批量插入
    * @Param: * @Param list: 
 * @Param id: 
 * @Param jsonStr: 
 * @Param indexName: 
 * @Param indexType: 
    * @Return: * @return: void
    * @date: 
    */
    public void batchInsert(List<Object> list,String id,String indexName,String indexType){
        int counter = 0;
        //判断index 是否存在
        if (!getTemplate().indexExists(indexName)) {
            getTemplate().createIndex(indexName);
        }
        List<IndexQuery> queries = new ArrayList<>();
        if(list != null && list.size()>0){
            for (Object object : list) {
                IndexQuery indexQuery = new IndexQuery();
               // indexQuery.setId(getId(object,id));
                indexQuery.setSource(JSON.toJSONString(object));
                indexQuery.setIndexName(indexName);
                indexQuery.setType(indexType);
                queries.add(indexQuery);
                //分批提交索引
                if (counter % 500 == 0) {
                    getTemplate().bulkIndex(queries);
                    queries.clear();
                }
                counter++;
            }
        }
        // 剩下不足批的提交
        if (queries.size() > 0) {
            getTemplate().bulkIndex(queries);
        }
        getTemplate().refresh(indexName);
    }

    private String getId(Object obj,String id) {
        Class clz = obj.getClass();
        Field[] field = clz.getDeclaredFields();
        String result = "";
        try {
            for (Field f : field) {
                String name = f.getName();
                String value = (String) f.get(obj);
                if (id.equals(name)) {
                    result = (String) f.get(obj);
                }
            }
        }catch (Exception e){
            log.error("异常",e);
        }
        return result;
    }

    /**
    * @Description: 删除数据
    * @Param: * @Param id: 
    * @Return: * @return: void
    * @date: 
    */
    public void delete(String id,Class clazz){
        getTemplate().delete(clazz,id);
    }
    /**
    * @Description: 删除索引
    * @Param: 
    * @Return: * @return: void
    * @date: 
    */
    public void deleteIndex(Class clazz){
        getTemplate().deleteIndex(clazz);
    }
}
