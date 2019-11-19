package com.shengsu.helper.mapper;

import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CodeGeneratorMapper extends BaseMapper<String, String> {

    String getCode(Map<String, String> parameterMap);

    String getLawcaseCode(Map<String, String> parameterMap);
}
