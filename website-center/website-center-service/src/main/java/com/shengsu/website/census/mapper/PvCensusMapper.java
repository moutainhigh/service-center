package com.shengsu.website.census.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.census.entity.PvCensus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PvCensusMapper extends BaseMapper<PvCensus,String> {

    int update();
}
