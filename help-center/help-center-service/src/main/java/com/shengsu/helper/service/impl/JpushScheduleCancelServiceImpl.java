package com.shengsu.helper.service.impl;

import com.shengsu.helper.entity.JpushSchedualRecord;
import com.shengsu.helper.entity.JpushScheduleCancel;
import com.shengsu.helper.mapper.JpushSchedualRecordMapper;
import com.shengsu.mq.message.MessageProcessor;
import com.shengsu.util.JiPushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bell on 2019/10/25.
 */
@Slf4j
@Service(value = "jpushScheduleCancelService")
public class JpushScheduleCancelServiceImpl implements MessageProcessor<JpushScheduleCancel> {
    @Autowired
    JiPushUtil JiPushUtil;
    @Autowired
    JpushSchedualRecordMapper jpushSchedualRecordMapper;

    @Override
    public boolean handleMessage(JpushScheduleCancel jpushScheduleCancel) {
        String messageId = jpushScheduleCancel.getMessageId();
        JpushSchedualRecord jpushSchedualRecord = jpushSchedualRecordMapper.selectByMsgId(messageId);
        if (jpushSchedualRecord != null) {
            try {
                JiPushUtil.deleteSchedule(jpushSchedualRecord.getScheduleId());
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
