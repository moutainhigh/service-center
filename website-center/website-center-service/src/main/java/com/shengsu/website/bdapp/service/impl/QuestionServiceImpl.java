package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.mapper.QuestionMapper;
import com.shengsu.website.bdapp.po.QuestionReplyPo;
import com.shengsu.website.bdapp.service.LawyerService;
import com.shengsu.website.bdapp.service.QuestionReplyService;
import com.shengsu.website.bdapp.service.QuestionService;
import com.shengsu.website.bdapp.util.QuestionUtils;
import com.shengsu.website.bdapp.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-09 17:28
 **/
@Service(value = "questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private LawyerService lawyerService;
    @Autowired
    private QuestionReplyService questionReplyService;

    @Override
    public BaseMapper getBaseMapper() {
        return questionMapper;
    }

    @Override
    public ResultBean create(Question question) {
        question.setQuestionId(UUID.randomUUID().toString());
        questionMapper.save(question);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean getDetail(QuestionVo questionVo) {
        Question question = questionMapper.get(questionVo.getQuestionId());
        QuestionReply questionReply = new QuestionReply();
        questionReply.setQuestionId(question.getQuestionId());
        QuestionReply reply = questionReplyService.getOne(questionReply);
        if(reply == null){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, question);
        }
        Lawyer lawyer = lawyerService.get(reply.getReplyLawyerId());
        QuestionReplyPo questionReplyPo = QuestionUtils.toQuestionReplyPo(question, lawyer, reply);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, questionReplyPo);
    }
}
