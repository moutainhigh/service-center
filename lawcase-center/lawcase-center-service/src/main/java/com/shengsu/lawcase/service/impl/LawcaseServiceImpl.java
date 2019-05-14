package com.shengsu.lawcase.service.impl;

import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.Lawcase;
import com.shengsu.lawcase.mapper.LawcaseMapper;
import com.shengsu.lawcase.service.LawcaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zxh on 2019/5/8.
 */
public class LawcaseServiceImpl extends BaseServiceImpl<Lawcase,String> implements LawcaseService{
    LawcaseMapper lawcaseMapper;

    @Autowired
    public void setLawcaseMapper(LawcaseMapper lawcaseMapper){
        this.lawcaseMapper = lawcaseMapper;
        this.baseMapper = lawcaseMapper;
    }
}
