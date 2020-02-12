package com.shengsu.any.clue.service.impl;

import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.mapper.CluePersonalMapper;
import com.shengsu.any.clue.service.CluePersonalService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("cluePersonalService")
public class CluePersonalServiceImpl extends BaseServiceImpl<CluePersonal, String> implements CluePersonalService {
    @Autowired
    private UserService userService;
    @Autowired
    private CluePersonalMapper cluePersonalMapper;
    @Override
    public BaseMapper<CluePersonal, String> getBaseMapper() {
        return cluePersonalMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(String clueId,String userId) {
        CluePersonal cluePersonal = new CluePersonal();
        cluePersonal.setClueId(clueId);
        cluePersonal.setUserId(userId);
        save(cluePersonal);
    }
}
