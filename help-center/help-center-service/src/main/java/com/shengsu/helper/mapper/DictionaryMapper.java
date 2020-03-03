package com.shengsu.helper.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.helper.entity.Dictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary, String> {
    List<Dictionary> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(Dictionary Dictionary);

    int dictExistCheckExceptSelf(Dictionary Dictionary);
}
