package com.shengsu.system.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.system.entity.SystemDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemDictionaryMapper extends BaseMapper<SystemDictionary,String> {
    List<SystemDictionary> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDictionary systemDictionary);

    int dictExistCheckExceptSelf(SystemDictionary systemDictionary);
}
