package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.LawcaseTaskPerson;
import com.shengsu.lawcase.mapper.LawcaseTaskPersonMapper;
import com.shengsu.lawcase.service.LawcaseTaskPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by lipiao on 2019/5/21.
 */
@Service("lawcaseTaskPersonService")
public class LawcaseTaskPersonServiceImpl extends BaseServiceImpl<LawcaseTaskPerson,String> implements LawcaseTaskPersonService {
    @Autowired
    LawcaseTaskPersonMapper lawcaseTaskPersonMapper;
    @Override
    public BaseMapper<LawcaseTaskPerson, String> getBaseMapper() {
        return lawcaseTaskPersonMapper;
    }

    @Override
    public List<LawcaseTaskPerson> getManyByCaseIds(List<String> caseIds) {
        return lawcaseTaskPersonMapper.getManyByCaseIds(caseIds);
    }
}
