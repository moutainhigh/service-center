package com.shengsu.helper.service.impl;

import com.shengsu.helper.entity.JpushSchedualRecord;
import com.shengsu.helper.entity.JpushScheduleCancel;
import com.shengsu.mapper.JpushSchedualRecordMapper;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bell on 2019/10/25.
 */
@Slf4j
@Service( value = "jpushScheduleCancelService")
public class JpushScheduleCancelServiceImpl implements MessageProcessor<JpushScheduleCancel> {
    @Autowired
    JiPushUtil JiPushUtil;
    @Autowired
    JpushSchedualRecordMapper jpushSchedualRecordMapper;
    @Override
    public boolean handleMessage(JpushScheduleCancel jpushScheduleCancel){
        String messageId = jpushScheduleCancel.getMessageId();

        JpushSchedualRecord jpushSchedualRecord = jpushSchedualRecordMapper.selectByMsgId(messageId);
        if(jpushSchedualRecord !=null){
            JiPushUtil.deleteSchedule(jpushSchedualRecord.getScheduleId());
        }
        return true;
    }
    @Override
    public Class<JpushScheduleCancel> getClazz() {
        return JpushScheduleCancel.class;
    }
}
