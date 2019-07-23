package com.shengsu.consult.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.consult.entity.Consult;
import com.shengsu.system.entity.LawcaseDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsultMapper extends BaseMapper<Consult,String> {

}
