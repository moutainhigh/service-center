package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.LawcasePhaseTask;
import com.shengsu.lawcase.entity.LawcaseTaskPerson;
import com.shengsu.lawcase.mapper.LawcasePhaseTaskMapper;
import com.shengsu.lawcase.mapper.LawcaseTaskPersonMapper;
import com.shengsu.lawcase.service.LawcasePhaseTaskService;
import com.shengsu.lawcase.service.LawcaseTaskPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by lipiao on 2019/5/21.
 */
@Service("lawcasePhaseTaskService")
public class LawcasePhaseTaskServiceImpl extends BaseServiceImpl<LawcasePhaseTask,String> implements LawcasePhaseTaskService {
    @Autowired
    LawcasePhaseTaskMapper lawcasePhaseTaskMapper;
    @Override
    public BaseMapper<LawcasePhaseTask, String> getBaseMapper() {
        return lawcasePhaseTaskMapper;
    }

    @Override
    public List<LawcasePhaseTask> getManyByCaseIds(List<String> caseIds) {
        return lawcasePhaseTaskMapper.getManyByCaseIds(caseIds);
    }
}
