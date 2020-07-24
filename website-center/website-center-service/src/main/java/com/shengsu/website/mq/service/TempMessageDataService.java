package com.shengsu.website.mq.service;

import com.shengsu.mq.MessageProcessor;
import com.shengsu.website.constant.TemplateMessageEnum;
import com.shengsu.website.market.entity.TempMessageData;
import com.shengsu.website.market.entity.TempMessageParamData;
import com.shengsu.website.market.service.TemplateMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-03-26 10:40
 **/
@Slf4j
@Service("tempMessageDataService")
public class TempMessageDataService implements MessageProcessor<TempMessageData> {
    @Autowired
    private TemplateMessageService templateMessageService;
    @Override
    public boolean handleMessage(TempMessageData tempMessageData) {
        log.info("处理消息："+ tempMessageData);
        String openId = tempMessageData.getOpenId();
        String knowledgeId = tempMessageData.getKnowledgeId();
        TempMessageParamData data = tempMessageData.getData();
        // 推送消息
        templateMessageService.pushTemplateMessage(openId,knowledgeId,TemplateMessageEnum.MESSAGE_TEMPLATE_LAWKNOWLEDGE_SEND,data);
        return true;
    }
    @Override
    public Class<TempMessageData> getClazz() {
        return TempMessageData.class;
    }
}
