package com.shengsu.website.home.service;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.home.entity.PvCensus;
import com.shengsu.website.home.mapper.PvCensusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pvCensusService")
public class PvCensusServiceImpl extends BaseServiceImpl implements PvCensusService {

    @Autowired
    private PvCensusMapper pvCensusMapper;

    @Override
    public BaseMapper<PvCensus, String> getBaseMapper() {
        return pvCensusMapper;
    }

    @Override
    public int update() {
        return pvCensusMapper.update();
    }


}
