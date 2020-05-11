package com.shengsu.website.bdapp.service.impl;

import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.bdapp.entity.ConsultAnswer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.service.ConsultAnswerService;
import com.shengsu.website.bdapp.service.QuestionReplyService;
import com.shengsu.website.bdapp.service.QuestionService;
import com.shengsu.website.bdapp.util.QuestionReplyUtils;
import com.shengsu.website.bdapp.util.QuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-11 11:25
 **/
@Service("consultAnswerService")
public class ConsultAnswerServiceImpl implements ConsultAnswerService {
    @Autowired
    private QuestionReplyService questionReplyService;
    @Autowired
    private QuestionService questionService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean create(ConsultAnswer consultAnswer){
        int count = questionService.isQuestionExist(consultAnswer.getQuestionContent());
        if(count>0){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_PROBLEM_ALREADY_EXISTS, null);
        }
        Question question = QuestionUtils.toQuestion(consultAnswer);
        questionService.create(question);
        QuestionReply questionReply = QuestionReplyUtils.toQuestionReply(consultAnswer,question);
        questionReplyService.create(questionReply);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }
}
