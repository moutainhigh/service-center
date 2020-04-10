package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.mapper.QuestionReplyMapper;
import com.shengsu.website.bdapp.po.QuestionReplyPo;
import com.shengsu.website.bdapp.service.LawyerService;
import com.shengsu.website.bdapp.service.QuestionReplyService;
import com.shengsu.website.bdapp.service.QuestionService;
import com.shengsu.website.bdapp.util.QuestionReplyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 11:31
 **/
@Service(value = "questionReplyService")
public class QuestionReplyServiceImpl extends BaseServiceImpl<QuestionReply, String> implements QuestionReplyService {
    @Autowired
    private QuestionReplyMapper questionReplyMapper;
    @Autowired
    private LawyerService lawyerService;
    @Autowired
    private QuestionService questionService;

    @Override
    public BaseMapper getBaseMapper() {
        return questionReplyMapper;
    }

    @Override
    public ResultBean create(QuestionReply questionReply) {
        questionReply.setReplyId(UUID.randomUUID().toString());
        questionReplyMapper.save(questionReply);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean selectAll() {
        List<QuestionReply> questionReplies = questionReplyMapper.listAll();
        List<String> lawyerIds = new ArrayList<>();
        for (QuestionReply questionReply : questionReplies) {
            String lawyerId = questionReply.getReplyLawyerId();
            lawyerIds.add(lawyerId);
        }
        List<Lawyer> lawyers = lawyerService.getMany(lawyerIds);
        List<String> questionIds = new ArrayList<>();
        for (QuestionReply questionReply : questionReplies) {
            String questionId = questionReply.getQuestionId();
            questionIds.add(questionId);
        }
        List<Question> questions = questionService.getMany(questionIds);
        List<QuestionReplyPo> questionReplyPos = QuestionReplyUtils.toQuestionReplyPos(questionReplies, lawyers, questions);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, questionReplyPos);
    }

    @Override
    public List<QuestionReply> getReplyByLawyer(String lawyerId) {
        return questionReplyMapper.getReplyByLawyer(lawyerId);
    }
}