package com.shengsu.helper.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.helper.entity.SystemDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemDictionaryMapper extends BaseMapper<SystemDictionary, String> {
    List<SystemDictionary> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDictionary SystemDictionary);

    int dictExistCheckExceptSelf(SystemDictionary SystemDictionary);
}
