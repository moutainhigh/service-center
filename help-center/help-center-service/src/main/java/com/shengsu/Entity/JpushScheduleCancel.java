package com.shengsu.Entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * Created by Bell on 2019/10/28.
 */
@Data
public class JpushScheduleCancel extends BaseEntity{
    private String messageId;
    private String scheduleId;
}
