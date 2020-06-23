package com.shengsu.website.mq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.helper.service.RedisService;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.website.home.service.LawcaseConsultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-20 16:10
 **/
@Slf4j
@Service("payNotifyService")
public class PayNotifyService implements MessageProcessor<JSONObject> {
    @Autowired
    private LawcaseConsultService lawcaseConsultService;
    @Resource
    private RedisService redisService;

    @Override
    public boolean handleMessage(JSONObject jsonObject) {
        log.info("处理消息："+ jsonObject);
        String outTradeNo = jsonObject.getString("outTradeNo");
        // 获取电话咨询信息
        String telConsultJson= (String)redisService.get(outTradeNo);
        if (StringUtils.isNotBlank(telConsultJson)){
            JSONObject object = JSON.parseObject(telConsultJson);
            String tel =object.getString("tel");
            String lawField =object.getString("lawField");
            String source =object.getString("source");
            // 保存咨询消息
            lawcaseConsultService.saveTelConsultData(tel,lawField,source);
            // 缓存清除
            redisService.delete(outTradeNo);
        }
        return true;
    }
    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }
}
