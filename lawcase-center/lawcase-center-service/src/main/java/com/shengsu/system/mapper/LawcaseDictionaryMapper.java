package com.shengsu.system.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.system.entity.LawcaseDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawcaseDictionaryMapper extends BaseMapper<LawcaseDictionary,String> {
    List<LawcaseDictionary> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(LawcaseDictionary lawcaseDictionary);

    int dictExistCheckExceptSelf(LawcaseDictionary lawcaseDictionary);
}
