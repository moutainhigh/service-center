package com.shengsu.mq.service;

import cn.jpush.api.schedule.ScheduleResult;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.mq.entity.JpushExtrasparam;
import com.shengsu.mq.entity.JpushMessage;
import com.shengsu.mq.entity.JpushSchedualRecord;
import com.shengsu.mq.entity.JpushSchedule;
import com.shengsu.mq.mapper.JpushSchedualRecordMapper;
import com.shengsu.mq.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Bell on 2019/10/25.
 */
@Slf4j
@Service(value = "jpushScheduleService")
public class JpushScheduleService implements MessageProcessor<JpushSchedule> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Autowired
    private JpushSchedualRecordMapper jpushSchedualRecordMapper;

    @Override
    public boolean handleMessage(JpushSchedule jpushSchedule) {
        JpushMessage message = jpushSchedule.getMessage();
        List<String> aliasList = jpushSchedule.getAliasList();
        Date date = jpushSchedule.getDate();
        String MsgType = jpushSchedule.getMsgType();
        String notificationTitle = jpushSchedule.getNotificationTitle();
        JpushExtrasparam extrasParam = jpushSchedule.getExtrasparam();
        String content = jpushSchedule.getContent();
        String name = jpushSchedule.getName();
        ScheduleResult scheduleResult = null;
        try {
            scheduleResult = jiPushUtil.sendSchedulePushList(message, aliasList, date, MsgType, notificationTitle, extrasParam, content, name);
        } catch (Exception e) {
            log.error("极光推送失败了:",e);
        }

        if (scheduleResult != null) {
            String scheduleId = scheduleResult.getSchedule_id();
            jpushSchedualRecordMapper.save(new JpushSchedualRecord(message.getMessageId(), scheduleId));
        }
        return true;
    }

    @Override
    public Class<JpushSchedule> getClazz() {
        return JpushSchedule.class;
    }
}
