package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper extends BaseMapper<Question,String> {
    int countAllByQuestionIds(List<String> questionIds);
    int isQuestionExist(String content);
    int isQuestionExistOther(Question question);
    List<Question> randomSelect(String systemTag);

    List<Question> getQuestions(Map<String,Object> map);
}
