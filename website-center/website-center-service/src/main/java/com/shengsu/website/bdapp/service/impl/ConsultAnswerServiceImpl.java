package com.shengsu.website.bdapp.service.impl;

import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.bdapp.entity.ConsultAnswer;
import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.po.QuestionReplyPo;
import com.shengsu.website.bdapp.service.ConsultAnswerService;
import com.shengsu.website.bdapp.service.LawyerService;
import com.shengsu.website.bdapp.service.QuestionReplyService;
import com.shengsu.website.bdapp.service.QuestionService;
import com.shengsu.website.bdapp.util.QuestionReplyUtils;
import com.shengsu.website.bdapp.util.QuestionUtils;
import com.shengsu.website.bdapp.vo.ConsultAnswerDeleteVo;
import com.shengsu.website.bdapp.vo.ConsultAnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private LawyerService lawyerService;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean update(ConsultAnswerVo consultAnswerVo){
        Question question = QuestionUtils.toQuestion(consultAnswerVo);

        int count = questionService.isQuestionExist(question);
        if(count>0){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_PROBLEM_ALREADY_EXISTS, null);
        }

        questionService.update(question);
        QuestionReply questionReply = QuestionReplyUtils.toQuestionReply(consultAnswerVo,question);
        questionReplyService.update(questionReply);

        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean delete(ConsultAnswerDeleteVo consultAnswerDeleteVo){
        questionService.delete(consultAnswerDeleteVo.getQuestionId());
        questionReplyService.delete(consultAnswerDeleteVo.getQuestionId());
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean questionReplyListByPage(Question question) {
        List<Question> questions = questionService.listByPage(question);
        if(questions.isEmpty()){
            return ResultUtil.formResult(true, ResultCode.SUCCESS,questions);
        }
        List<String> questionIds = new ArrayList<>();
        for (Question ques : questions) {
            String questionId = ques.getQuestionId();
            questionIds.add(questionId);
        }
        List<QuestionReply> questionReplies = questionReplyService.getMany(questionIds);
        if(questionReplies.isEmpty()){
            return ResultUtil.formResult(true, ResultCode.SUCCESS,questionReplies);
        }
        List<String> lawyerIds = new ArrayList<>();
        for (QuestionReply questionReplyList : questionReplies) {
            String lawyerId = questionReplyList.getReplyLawyerId();
            lawyerIds.add(lawyerId);
        }
        List<Lawyer> lawyers = lawyerService.getMany(lawyerIds);
        questionReplyService.geturls(lawyers);
        int totalCount = questionService.countAll(question);
        List<QuestionReplyPo> questionReplyPos = QuestionReplyUtils.toQuestionReplyPos(questionReplies, lawyers, questions);
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, questionReplyPos,totalCount);
    }

}
