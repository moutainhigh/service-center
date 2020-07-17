package com.shengsu.website.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.service.NewsCenterService;
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
@Service("esNewsCenterService")
public class EsNewsCenterService extends BaseElasticsearchService implements MessageProcessor<JSONObject> {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private NewsCenterService newsCenterService;
    @Override
    protected ElasticsearchTemplate getTemplate() {
        return elasticsearchTemplate;
    }
    @Override
    public boolean handleMessage(JSONObject jsonObject) {
        String operateType = jsonObject.getString("operateType");
        if (OPERATE_TYPE_CREATE.equals(operateType)){
            // 单个创建
            String newsId = jsonObject.getString("newsId");
            // 获取新闻中心对象
            NewsCenter newsCenter = newsCenterService.get(newsId);
            // 创建索引并插入数据
            index(newsCenter);
        }
        if (OPERATE_TYPE_UPDATE.equals(operateType)){
            // 更新
            String newsId = jsonObject.getString("newsId");
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            // 封装要更新的数据
            Map<String,Object> source = new HashMap<>();
            source.put("title",title);
            source.put("content",content);
            update(source,newsId,NewsCenter.class);
        }
        if (OPERATE_TYPE_BATCHCREATE.equals(operateType)){
            // 批量创建
            List<Object> list = new ArrayList<>();
            list.add(new NewsCenter());
            batchInsert(list,"newsId",NEWSCENTERE_INDEX_NAME,NEWSCENTER_INDEX_TYPE);
        }
        return true;
    }

    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }


}
