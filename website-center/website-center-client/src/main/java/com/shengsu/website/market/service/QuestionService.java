package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.vo.QuestionVo;
import com.shengsu.website.market.vo.SystemTagVo;

import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-09 17:30
 **/
public interface QuestionService extends BaseService<Question,String> {
    ResultBean create(Question question);
    ResultBean getDetail(QuestionVo questionVo);
    int countAllByQuestionIds(List<String> questionIds);
    int isQuestionExist(String content);
    int isQuestionExist(Question question);
    List<Question> randomSelect(String systemTag);
    List<Question> getQuestions(String systemTag, List<String> questionIds);
}
