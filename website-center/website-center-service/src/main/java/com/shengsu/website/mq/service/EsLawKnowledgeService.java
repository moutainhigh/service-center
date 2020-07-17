package com.shengsu.website.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.website.market.entity.LawKnowledge;
import com.shengsu.website.market.service.LawKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
@Service("esLawKnowledgeService")
public class EsLawKnowledgeService extends BaseElasticsearchService implements MessageProcessor<JSONObject> {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private LawKnowledgeService lawKnowledgeService;
    @Override
    protected ElasticsearchTemplate getTemplate() {
        return elasticsearchTemplate;
    }
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
            update(source,knowledgeId,LawKnowledge.class);
        }
        if (OPERATE_TYPE_BATCHCREATE.equals(operateType)){
            // 批量创建
            List<Object> list = new ArrayList<>();
            list.add(new LawKnowledge());
            batchInsert(list,"knowledgeId",LAWKNOWLEDGE_INDEX_NAME,LAWKNOWLEDGE_INDEX_TYPE);
        }
        return true;
    }

    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }


}
