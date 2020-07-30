package com.shengsu.website.market.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.mapper.QuestionMapper;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.entity.QuestionReply;
import com.shengsu.website.market.po.QuestionReplyPo;
import com.shengsu.website.market.service.LawyerService;
import com.shengsu.website.market.service.QuestionReplyService;
import com.shengsu.website.market.service.QuestionService;
import com.shengsu.website.market.util.QuestionUtils;
import com.shengsu.website.market.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private OssService ossService;

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
        if(question == null){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, question);
        }
        QuestionReply questionReply = new QuestionReply();
        questionReply.setQuestionId(question.getQuestionId());
        QuestionReply reply = questionReplyService.getOne(questionReply);
        if(reply == null){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, question);
        }
        Lawyer lawyer = lawyerService.get(reply.getReplyLawyerId());
        String url = ossService.getUrl(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR, lawyer.getIconOssResourceId());
        lawyer.setIconOssResourceId(url);
        QuestionReplyPo questionReplyPo = QuestionUtils.toQuestionReplyPo(question, lawyer, reply);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, questionReplyPo);
    }

    @Override
    public int countAllByQuestionIds(List<String> questionIds) {
        return questionMapper.countAllByQuestionIds(questionIds);
    }
    @Override
    public int isQuestionExist(String content){
        return questionMapper.isQuestionExist(content);
    }

    @Override
    public int isQuestionExist(Question question) {
        return questionMapper.isQuestionExistOther(question);
    }
    @Override
    public List<Question> randomSelect(String systemTag){
        return questionMapper.randomSelect(systemTag);
    }

    @Override
    public List<Question> getQuestions(String systemTag, List<String> questionIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("systemTag", systemTag);
        map.put("questionIds", questionIds);
        return questionMapper.getQuestions(map);
    }

}
