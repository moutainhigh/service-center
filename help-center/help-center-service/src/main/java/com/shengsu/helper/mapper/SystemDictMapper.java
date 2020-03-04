package com.shengsu.helper.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.helper.entity.SystemDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SystemDictMapper extends BaseMapper<SystemDict, String> {
    List<SystemDict> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDict SystemDict);

    int dictExistCheckExceptSelf(SystemDict SystemDict);

    SystemDict getOneByDisplayValue(Map<String, Object> map);

    List<SystemDict> listByDictCode(String dictCode);

    List<SystemDict> getManyByDisplayValue(Map<String, Object> map);
}
