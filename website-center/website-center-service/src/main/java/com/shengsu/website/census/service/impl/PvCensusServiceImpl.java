package com.shengsu.website.census.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.census.entity.PvCensus;
import com.shengsu.website.census.mapper.PvCensusMapper;
import com.shengsu.website.census.service.PvCensusService;
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
