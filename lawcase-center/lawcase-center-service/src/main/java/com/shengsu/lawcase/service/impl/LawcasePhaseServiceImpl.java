package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.LawcasePhase;
import com.shengsu.lawcase.entity.LawcasePhaseTask;
import com.shengsu.lawcase.mapper.LawcasePhaseMapper;
import com.shengsu.lawcase.mapper.LawcasePhaseTaskMapper;
import com.shengsu.lawcase.service.LawcasePhaseService;
import com.shengsu.lawcase.service.LawcasePhaseTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by lipiao on 2019/5/21.
 */
@Service("lawcasePhaseService")
public class LawcasePhaseServiceImpl extends BaseServiceImpl<LawcasePhase,String> implements LawcasePhaseService {
    @Autowired
    LawcasePhaseMapper lawcasePhaseMapper;
    @Override
    public BaseMapper<LawcasePhase, String> getBaseMapper() {
        return lawcasePhaseMapper;
    }

    @Override
    public List<LawcasePhase> getManyByCaseIds(List<String> caseIds) {
        return lawcasePhaseMapper.getManyByCaseIds(caseIds);
    }
}
