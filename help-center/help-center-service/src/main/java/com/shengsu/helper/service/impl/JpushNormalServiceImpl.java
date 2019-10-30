package com.shengsu.helper.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shengsu.Entity.JpushNormal;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Bell on 2019/10/24.
 */
@Slf4j
@Service
public class JpushNormalServiceImpl implements MessageProcessor<JpushNormal> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Override
    public boolean handleMessage(JpushNormal jpushNormal, String ...ags){
        List<String> aliasList= jpushNormal.getAliasList();
        String notification_title= jpushNormal.getNotificationTitle();
        String msg_title= jpushNormal.getMsgTitle();
        String msg_content= jpushNormal.getMsgContent();
        String extrasparam= jpushNormal.getExtrasparam();
        System.out.println(notification_title+ msg_content);
        jiPushUtil.sendToAliasList(aliasList,notification_title,msg_title,msg_content,extrasparam);
        return true;
    }
    @Override
    public Class<JpushNormal> getClazz() {
        return JpushNormal.class;
    }
}
