package com.shengsu.mapper;

import com.shengsu.Entity.JpushScheduleCancel;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Bell on 2019/10/28.
 */
@Mapper
public interface JPushMapper {
    String list(JpushScheduleCancel jpushScheduleCancel);
    int save(JpushScheduleCancel jpushScheduleCancel);
}
