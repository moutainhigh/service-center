package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.mapper.QuestionMapper;
import com.shengsu.website.bdapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-09 17:28
 **/
@Service(value = "questionService")
public class QuestionServiceImpl extends BaseServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return questionMapper;
    }
    @Override
    public ResultBean create(Question question){
        question.setQuestionId(UUID.randomUUID().toString());
        questionMapper.save(question);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }
}
