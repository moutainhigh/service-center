package com.shengsu.helper.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shengsu.Entity.JpushScheduleCancel;
import com.shengsu.mapper.JPushMapper;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Bell on 2019/10/25.
 */
@Slf4j
@Service
public class JpushScheduleCancelServiceImpl implements MessageProcessor<JpushScheduleCancel> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Autowired
    JPushMapper jPushMapper;
    @Override
    public boolean handleMessage(JpushScheduleCancel jpushScheduleCancel, String ...ags){
        String scheduleId= jPushMapper.list(jpushScheduleCancel);
        jiPushUtil.DeleteSchedule(scheduleId);
        return true;
    }
    @Override
    public Class<JpushScheduleCancel> getClazz() {
        return JpushScheduleCancel.class;
    }
}
