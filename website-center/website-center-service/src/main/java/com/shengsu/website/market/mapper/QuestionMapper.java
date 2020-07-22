package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question,String> {
    int countAllByQuestionIds(List<String> questionIds);
    int isQuestionExist(String content);
    int isQuestionExistOther(Question question);
    List<Question> randomSelect();
}
