package com.shengsu.log.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.log.entity.LogBusiness;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessLogMapper extends BaseMapper<LogBusiness,String> {
}
