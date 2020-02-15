package com.shengsu.any.clue.mapper;


import com.shengsu.any.clue.entity.ClueRecord;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:46
 **/
@Mapper
public interface ClueRecordMapper extends BaseMapper<ClueRecord,String> {
    List<ClueRecord> listMyClueRecord(ClueRecord clueRecord);
}
