package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.LawcasePerson;
import com.shengsu.lawcase.mapper.LawcasePersonMapper;
import com.shengsu.lawcase.service.LawcasePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lipiao on 2019/5/21.
 */
@Service("lawcasePersonService")
public class LawcasePersonServiceImpl extends BaseServiceImpl<LawcasePerson,String> implements LawcasePersonService {
    @Autowired
    LawcasePersonMapper lawcasePersonMapper;
    @Override
    public BaseMapper<LawcasePerson, String> getBaseMapper() {
        return lawcasePersonMapper;
    }

    @Override
    public List<LawcasePerson> getManyByCaseIds(List<String> caseIds) {
        return lawcasePersonMapper.getManyByCaseIds(caseIds);
    }
}
