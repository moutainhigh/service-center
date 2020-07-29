package com.shengsu.mq.service;

import com.shengsu.mq.MessageProcessor;
import com.shengsu.mq.entity.JpushSchedualRecord;
import com.shengsu.mq.entity.JpushScheduleCancel;
import com.shengsu.mq.mapper.JpushSchedualRecordMapper;
import com.shengsu.mq.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bell on 2019/10/25.
 */
@Slf4j
@Service(value = "jpushScheduleCancelService")
public class JpushScheduleCancelService implements MessageProcessor<JpushScheduleCancel> {
    @Autowired
    JiPushUtil jiPushUtil;
    @Autowired
    JpushSchedualRecordMapper jpushSchedualRecordMapper;

    @Override
    public boolean handleMessage(JpushScheduleCancel jpushScheduleCancel) {
        String messageId = jpushScheduleCancel.getMessageId();
        JpushSchedualRecord jpushSchedualRecord = jpushSchedualRecordMapper.selectByMsgId(messageId);
        if (jpushSchedualRecord != null) {
            try {
                jiPushUtil.deleteSchedule(jpushSchedualRecord.getScheduleId());
            } catch (Exception e) {
                log.error("极光推送失败了:",e);
            }
        }
        return true;
    }

    @Override
    public Class<JpushScheduleCancel> getClazz() {
        return JpushScheduleCancel.class;
    }
}
