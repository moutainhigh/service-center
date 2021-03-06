package com.shengsu.mq.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.mq.entity.JpushSchedualRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Bell on 2019/10/28.
 */
@Mapper
public interface JpushSchedualRecordMapper extends BaseMapper<JpushSchedualRecord, String> {
    JpushSchedualRecord selectByMsgId(String msgId);
}
