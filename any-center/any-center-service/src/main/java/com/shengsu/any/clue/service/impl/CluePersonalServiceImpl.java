package com.shengsu.any.clue.service.impl;

import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.mapper.CluePersonalMapper;
import com.shengsu.any.clue.service.CluePersonalService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("cluePersonalService")
public class CluePersonalServiceImpl extends BaseServiceImpl<CluePersonal, String> implements CluePersonalService {
    @Autowired
    private CluePersonalMapper cluePersonalMapper;
    @Override
    public BaseMapper<CluePersonal, String> getBaseMapper() {
        return cluePersonalMapper;
    }
}
