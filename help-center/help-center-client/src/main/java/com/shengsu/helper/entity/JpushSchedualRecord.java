package com.shengsu.helper.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * Created by Bell on 2019/10/30.
 */
@Data
public class JpushSchedualRecord  extends BaseEntity{

    private Long id;
    private String messageId;
    private String scheduleId;

    public JpushSchedualRecord(String messageId, String scheduleId) {
        this.messageId = messageId;
        this.scheduleId = scheduleId;
    }

    public JpushSchedualRecord() {
    }
}
