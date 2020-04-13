package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.bdapp.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question,String> {
    int countAllByQuestionIds(List<String> questionIds);
}
