package com.shengsu.helper.service.impl;

import cn.jpush.api.schedule.ScheduleResult;
import com.shengsu.helper.entity.JpushSchedualRecord;
import com.shengsu.helper.entity.JpushSchedule;
import com.shengsu.mapper.JpushSchedualRecordMapper;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
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
public class JpushScheduleServiceImpl implements MessageProcessor<JpushSchedule> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Autowired
    private JpushSchedualRecordMapper jpushSchedualRecordMapper;
    @Override
    public boolean handleMessage(JpushSchedule jpushSchedule, String ...ags) {
        String obj = jpushSchedule.getObj();
        List<String> aliasList = jpushSchedule.getAliasList();
        Date date = jpushSchedule.getDate();
        String MsgType = jpushSchedule.getMsgType();
        String notification_title = jpushSchedule.getNotificationTitle();
        String extrasparam = jpushSchedule.getExtrasparam();
        String content = jpushSchedule.getContent();
        String name = jpushSchedule.getName();
        ScheduleResult scheduleResult = jiPushUtil.sendSchedulePushList(obj, aliasList, date, MsgType, notification_title, extrasparam, content, name);
        String scheduleId=scheduleResult.getSchedule_id();
        jpushSchedualRecordMapper.save( new JpushSchedualRecord(ags[0],scheduleId));
        return true;
    }

    @Override
    public Class<JpushSchedule> getClazz() {
        return JpushSchedule.class;
    }
}
