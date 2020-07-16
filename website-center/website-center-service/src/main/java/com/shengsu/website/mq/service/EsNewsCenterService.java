package com.shengsu.website.mq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.service.NewsCenterService;
import com.shengsu.website.market.entity.LawKnowledge;
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
@Service("esNewsCenterService")
public class EsNewsCenterService extends BaseElasticsearchService implements MessageProcessor<JSONObject> {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private NewsCenterService newsCenterService;
    @Override
    public boolean handleMessage(JSONObject jsonObject) {
        String operateType = jsonObject.getString("operateType");
        if (OPERATE_TYPE_CREATE.equals(operateType)){
            // 单个创建
            String title = jsonObject.getString("title");
            // 获取新闻中心对象
            NewsCenter newsCenter = newsCenterService.selectByTitle(title);
            // 创建索引并插入数据
            index(newsCenter);
        }
        if (OPERATE_TYPE_UPDATE.equals(operateType)){
            // 单个更新
            String id = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            // 封装要更新的数据
            Map<String,Object> source = new HashMap<>();
            source.put("title",title);
            source.put("content",content);
            update(source,id);
        }
        if (OPERATE_TYPE_BATCHCREATE.equals(operateType)){
            // 批量创建
            batchInsert(new ArrayList<>());
        }
        return true;
    }
    /**
    * @Description: 单条数据保存
    * @Param: * @Param indexObject: 
    * @Return: * @return: void
    * @date: 
    */
    protected void index(Object indexObject) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(indexObject).build();
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    protected void update(Map<String, Object> source, String id) {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(source);
        UpdateQuery query = new UpdateQueryBuilder().withClass(NewsCenter.class)
                .withId(id)
                .withIndexRequest(indexRequest)
                .build();
        elasticsearchTemplate.update(query);
    }

    @Override
    protected void delete(String id) {
        elasticsearchTemplate.delete(LawKnowledge.class,id);
    }

    @Override
    protected void deleteIndex() {
        elasticsearchTemplate.deleteIndex(LawKnowledge.class);
    }

    /**
    * @Description: 单条数据更新
    * @Param: * @Param source: 
 * @Param knowledgeId:
    * @Return: * @return: void
    * @date: 
    */
    public void updateNewsCenter(Map<String,Object> source,String id) {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(source);
        UpdateQuery query = new UpdateQueryBuilder().withClass(NewsCenter.class)
                .withId(id)
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
    public void batchInsert(List<NewsCenter> newsCenters){
        int counter = 0;
        //判断index 是否存在
        if (!elasticsearchTemplate.indexExists(NEWSCENTERE_INDEX_NAME)) {
            elasticsearchTemplate.createIndex(NEWSCENTERE_INDEX_NAME);
        }
        List<IndexQuery> queries = new ArrayList<>();
        if(newsCenters != null && newsCenters.size()>0){
            for (NewsCenter newsCenter : newsCenters) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(newsCenter.getId().toString());
                indexQuery.setSource(JSON.toJSONString(newsCenter));
                indexQuery.setIndexName(NEWSCENTERE_INDEX_NAME);
                indexQuery.setType(NEWSCENTER_INDEX_TYPE);
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
        elasticsearchTemplate.refresh(NEWSCENTERE_INDEX_NAME);
    }
    /**
    * @Description: 删除法律文库数据
    * @Param:
    * @Return: * @return: void
    * @date: 
    */
    public void deleteLawknowledgeDoc(String id){
        elasticsearchTemplate.delete(NewsCenter.class,id);
    }
    /**
     * @Description: 删除法律文库索引
     * @Param:
     * @Return: * @return: void
     * @date:
     */
    public void deleteLawKnowledgeIndex(){
        elasticsearchTemplate.deleteIndex(NewsCenter.class);
    }

    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }


}
