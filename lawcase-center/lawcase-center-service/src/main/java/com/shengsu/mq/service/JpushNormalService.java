package com.shengsu.mq.service;


import com.shengsu.mq.MessageProcessor;
import com.shengsu.mq.entity.JpushExtrasparam;
import com.shengsu.mq.entity.JpushNormal;
import com.shengsu.mq.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bell on 2019/10/24.
 */
@Slf4j
@Service(value = "jpushNormalService")
public class JpushNormalService implements MessageProcessor<JpushNormal> {
    @Autowired
    JiPushUtil jiPushUtil;

    @Override
    public boolean handleMessage(JpushNormal jpushNormal) {
        List<String> aliasList = jpushNormal.getAliasList();
        String notificationTitle = jpushNormal.getNotificationTitle();
        String msgTitle = jpushNormal.getMsgTitle();
        String msgContent = jpushNormal.getMsgContent();
        JpushExtrasparam extrasParam = jpushNormal.getExtrasparam();
        try {
            jiPushUtil.sendToAliasList(aliasList, notificationTitle, msgTitle, msgContent, extrasParam);
        } catch (Exception e) {
            log.error("极光推送失败了:",e);
        }
        return true;
    }

    @Override
    public Class<JpushNormal> getClazz() {
        return JpushNormal.class;
    }
}
