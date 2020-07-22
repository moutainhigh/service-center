package com.shengsu.website.market.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.entity.QuestionReply;
import com.shengsu.website.market.mapper.QuestionReplyMapper;
import com.shengsu.website.market.po.QuestionReplyPo;
import com.shengsu.website.market.service.LawyerService;
import com.shengsu.website.market.service.QuestionReplyService;
import com.shengsu.website.market.service.QuestionService;
import com.shengsu.website.market.util.QuestionReplyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private OssService ossService;

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
        List<QuestionReply> questionReplies = questionReplyMapper.listQuestionReply();
        if (CollectionUtils.isEmpty(questionReplies)){
            return ResultUtil.formResult(true, ResultCode.SUCCESS,null);
        }
        List<String> lawyerIds = new ArrayList<>();
        for (QuestionReply questionReply : questionReplies) {
            String lawyerId = questionReply.getReplyLawyerId();
            lawyerIds.add(lawyerId);
        }
        List<Lawyer> lawyers = lawyerService.getMany(lawyerIds);
        geturls(lawyers);
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
    @Override
    public ResultBean randomThree(){
        List<Question> questions = questionService.randomSelect();
        if(questions.isEmpty()){
            return ResultUtil.formResult(true, ResultCode.SUCCESS,questions);
        }
        List<String> questionIds = new ArrayList<>();
        for (Question ques : questions) {
            String questionId = ques.getQuestionId();
            questionIds.add(questionId);
        }
        List<QuestionReply> questionReplies = questionReplyMapper.getMany(questionIds);
        if(questionReplies.isEmpty()){
            return ResultUtil.formResult(true, ResultCode.SUCCESS,questionReplies);
        }
        List<String> lawyerIds = new ArrayList<>();
        for (QuestionReply questionReplyList : questionReplies) {
            String lawyerId = questionReplyList.getReplyLawyerId();
            lawyerIds.add(lawyerId);
        }
        List<Lawyer> lawyers = lawyerService.getMany(lawyerIds);
        geturls(lawyers);
        List<QuestionReplyPo> questionReplyPos = QuestionReplyUtils.toQuestionReplyPos(questionReplies, lawyers, questions);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, questionReplyPos);
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
        List<QuestionReply> questionReplies = questionReplyMapper.getMany(questionIds);
        if(questionReplies.isEmpty()){
            return ResultUtil.formResult(true, ResultCode.SUCCESS,questionReplies);
        }
        List<String> lawyerIds = new ArrayList<>();
        for (QuestionReply questionReplyList : questionReplies) {
            String lawyerId = questionReplyList.getReplyLawyerId();
            lawyerIds.add(lawyerId);
        }
        List<Lawyer> lawyers = lawyerService.getMany(lawyerIds);
        geturls(lawyers);
        int totalCount = questionService.countAll(question);
        List<QuestionReplyPo> questionReplyPos = QuestionReplyUtils.toQuestionReplyPos(questionReplies, lawyers, questions);
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, questionReplyPos,totalCount);
    }

    @Override
    public void geturls(List<Lawyer> lawyers){
        List<String> keys = new ArrayList<>();
        for(Lawyer lawyer : lawyers){
            String key = lawyer.getIconOssResourceId();
            keys.add(key);
        }
        Map<String,String> map = ossService.getUrls(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,keys);
            for(Lawyer lawyer : lawyers){
                    lawyer.setIconOssResourceId(map.get(lawyer.getIconOssResourceId()));
            }
    }
}
