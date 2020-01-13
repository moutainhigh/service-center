package com.shengsu.any.system.mapper;

import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SystemDictMapper extends BaseMapper<SystemDict,String> {
    SystemDict getOneByDisplayVlue(Map<String,Object> map);

    List<SystemDict> listByDictCode(String dictCode);

    List<SystemDict> getManyByDisplayVlue(Map<String,Object> map);
}
