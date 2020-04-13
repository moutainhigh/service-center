package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.vo.QuestionVo;

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
}
