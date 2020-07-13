package com.shengsu.website.mq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.website.market.entity.LawKnowledge;
import com.shengsu.website.market.service.LawKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shengsu.website.app.constant.BizConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-01 16:56
 **/
@Slf4j
@Service("elasticsearchService")
public class ElasticsearchService implements MessageProcessor<JSONObject> {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private LawKnowledgeService lawKnowledgeService;
    @Override
    public boolean handleMessage(JSONObject jsonObject) {
        String operateType = jsonObject.getString("operateType");
        if (OPERATE_TYPE_CREATE.equals(operateType)){
            // 单个创建
            String knowledgeId = jsonObject.getString("knowledgeId");
            // 获取知识文库对象
            LawKnowledge lawKnowledge = lawKnowledgeService.get(knowledgeId);
            // 创建索引并插入数据
            index(lawKnowledge);
        }
        if (OPERATE_TYPE_UPDATE.equals(operateType)){
            // 单个更新
            String knowledgeId = jsonObject.getString("knowledgeId");
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            // 封装要更新的数据
            Map<String,Object> source = new HashMap<>();
            source.put("title",title);
            source.put("content",content);
            updateLawKnowledgeDoc(source,knowledgeId);
        }
        if (OPERATE_TYPE_BATCHCREATE.equals(operateType)){
            // 批量创建
            batchInsert(new ArrayList<LawKnowledge>());
        }
        return true;
    }
    /**
    * @Description: 单条数据保存
    * @Param: * @Param indexObject: 
    * @Return: * @return: void
    * @date: 
    */
    private void index(Object indexObject) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(indexObject).build();
        elasticsearchTemplate.index(indexQuery);
    }
    /**
    * @Description: 单条数据更新
    * @Param: * @Param source: 
 * @Param knowledgeId:
    * @Return: * @return: void
    * @date: 
    */
    public void updateLawKnowledgeDoc(Map<String,Object> source,String knowledgeId) {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(source);
        UpdateQuery query = new UpdateQueryBuilder().withClass(LawKnowledge.class)
                .withId(knowledgeId)
                .withIndexRequest(indexRequest)
                .build();
        elasticsearchTemplate.update(query);
    }
    /**
    * @Description: 批量数据保存
    * @Param: * @Param lawKnowledges: 
    * @Return: * @return: void
    * @date: 
    */
    public void batchInsert(List<LawKnowledge> lawKnowledges){
        int counter = 0;
        //判断index 是否存在
        if (!elasticsearchTemplate.indexExists(LAWKNOWLEDGE_INDEX_NAME)) {
            elasticsearchTemplate.createIndex(LAWKNOWLEDGE_INDEX_NAME);
        }
        List<IndexQuery> queries = new ArrayList<>();
        if(lawKnowledges != null && lawKnowledges.size()>0){
            for (LawKnowledge lawKnowledge : lawKnowledges) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(lawKnowledge.getKnowledgeId());
                indexQuery.setSource(JSON.toJSONString(lawKnowledge));
                indexQuery.setIndexName(LAWKNOWLEDGE_INDEX_NAME);
                indexQuery.setType(LAWKNOWLEDGE_INDEX_TYPE);
                queries.add(indexQuery);
                //分批提交索引
                if (counter % 500 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                }
                counter++;
            }
        }
        // 剩下不足批的提交
        if (queries.size() > 0) {
            elasticsearchTemplate.bulkIndex(queries);
        }
        elasticsearchTemplate.refresh(LAWKNOWLEDGE_INDEX_NAME);
    }
    /**
    * @Description: 删除法律文库数据
    * @Param:
    * @Return: * @return: void
    * @date: 
    */
    public void deleteLawknowledgeDoc(String knowledgeId){
        elasticsearchTemplate.delete(LawKnowledge.class,knowledgeId);
    }
    /**
     * @Description: 删除法律文库索引
     * @Param:
     * @Return: * @return: void
     * @date:
     */
    public void deleteLawKnowledgeIndex(){
        elasticsearchTemplate.deleteIndex(LawKnowledge.class);
    }

    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }


}
