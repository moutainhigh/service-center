package com.shengsu.any.clue.service.impl;

import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.mapper.ClueMapper;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("clueService")
public class ClueServiceImpl extends BaseServiceImpl<Clue, String> implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Override
    public BaseMapper<Clue, String> getBaseMapper() {
        return clueMapper;
    }
}
