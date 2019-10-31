package com.shengsu.helper.service.impl;


import com.shengsu.helper.entity.Extrasparam;
import com.shengsu.helper.entity.JpushNormal;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bell on 2019/10/24.
 */
@Slf4j
@Service(value = "jpushNormalService")
public class JpushNormalServiceImpl implements MessageProcessor<JpushNormal> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Override
    public boolean handleMessage(JpushNormal jpushNormal, String ...otherParam){
        List<String> aliasList= jpushNormal.getAliasList();
        String notificationTitle= jpushNormal.getNotificationTitle();
        String msgTitle= jpushNormal.getMsgTitle();
        String msgContent= jpushNormal.getMsgContent();
        Extrasparam extrasParam = jpushNormal.getExtrasparam();
        jiPushUtil.sendToAliasList(aliasList,notificationTitle,msgTitle,msgContent,extrasParam);
        return true;
    }
    @Override
    public Class<JpushNormal> getClazz() {
        return JpushNormal.class;
    }
}
