package com.shengsu.any.mq.service;

import com.shengsu.any.constant.TemplateMessageEnum;
import com.shengsu.any.wechat.entity.TempMessageData;
import com.shengsu.any.wechat.entity.TempMessageData410928703;
import com.shengsu.any.wechat.service.TemplateMessageService;
import com.shengsu.mq.MessageProcessor;
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
        TempMessageData410928703 data = tempMessageData.getData();
        // 推送消息
        templateMessageService.pushTemplateMessage(openId,TemplateMessageEnum.MESSAGE_TEMPLATE_USER_AUTHROTION,data);
        return true;
    }
    @Override
    public Class<TempMessageData> getClazz() {
        return TempMessageData.class;
    }
}
