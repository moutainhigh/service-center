package com.shengsu.helper.service.impl;

import cn.jpush.api.schedule.ScheduleResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.shengsu.Entity.JpushScheduleCancel;
import com.shengsu.Entity.JpushSchedule;
import com.shengsu.mapper.JPushMapper;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Bell on 2019/10/25.
 */
@Slf4j
@Service
public class JpushScheduleServiceImpl implements MessageProcessor<JpushSchedule> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Autowired
    private JPushMapper jPushMapper;
    @Override
    public boolean handleMessage(JpushSchedule jpushSchedule, String ...ags) {
        Object obj = jpushSchedule.getObj();
        List<String> aliasList = jpushSchedule.getAliasList();
        Date date = jpushSchedule.getDate();
        String MsgType = jpushSchedule.getMsgType();
        String notification_title = jpushSchedule.getNotificationTitle();
        String extrasparam = jpushSchedule.getExtrasparam();
        String content = jpushSchedule.getContent();
        String name = jpushSchedule.getName();
        ScheduleResult scheduleResult = jiPushUtil.sendSchedulePushList(obj, aliasList, date, MsgType, notification_title, extrasparam, content, name);
        String scheduleId=scheduleResult.getSchedule_id();
        String msgId=ags[0];
        JpushScheduleCancel jpushScheduleCancel =new JpushScheduleCancel();
        jpushScheduleCancel.setMessageId(msgId);
        jpushScheduleCancel.setScheduleId(scheduleId);
        jPushMapper.save(jpushScheduleCancel);
        return true;
    }

    @Override
    public Class<JpushSchedule> getClazz() {
        return JpushSchedule.class;
    }
}
