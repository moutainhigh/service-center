package com.shengsu.helper.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.helper.entity.SystemDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemDictMapper extends BaseMapper<SystemDict, String> {
    List<SystemDict> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDict SystemDict);

    int dictExistCheckExceptSelf(SystemDict SystemDict);
}
