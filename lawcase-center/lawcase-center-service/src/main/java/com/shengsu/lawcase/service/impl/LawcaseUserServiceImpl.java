package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.LawcaseUser;
import com.shengsu.lawcase.mapper.LawcaseUserMapper;
import com.shengsu.lawcase.service.LawcaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by lipiao on 2019/5/21.
 */
@Service("lawcaseUserService")
public class LawcaseUserServiceImpl extends BaseServiceImpl<LawcaseUser,String> implements LawcaseUserService {
    @Autowired
    LawcaseUserMapper lawcaseUserMapper;
    @Override
    public BaseMapper<LawcaseUser, String> getBaseMapper() {
        return lawcaseUserMapper;
    }
}
