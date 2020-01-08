package com.shengsu.any.clue.service.impl;

import com.shengsu.any.clue.entity.ClueRecord;
import com.shengsu.any.clue.mapper.ClueRecordMapper;
import com.shengsu.any.clue.service.ClueRecordService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("clueRecordService")
public class ClueRecordServiceImpl extends BaseServiceImpl<ClueRecord, String> implements ClueRecordService {
    @Autowired
    private ClueRecordMapper clueRecordMapper;
    @Override
    public BaseMapper<ClueRecord, String> getBaseMapper() {
        return clueRecordMapper;
    }
}
