package com.shengsu.consult.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.consult.entity.Consult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConsultMapper extends BaseMapper<Consult,String> {

}
