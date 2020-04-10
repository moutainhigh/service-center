package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.mapper.LawyerMapper;
import com.shengsu.website.bdapp.po.QuestionReplyPo;
import com.shengsu.website.bdapp.service.LawyerService;
import com.shengsu.website.bdapp.service.QuestionReplyService;
import com.shengsu.website.bdapp.service.QuestionService;
import com.shengsu.website.bdapp.util.LawyerUtils;
import com.shengsu.website.bdapp.vo.LawyerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 13:23
 **/
@Service(value = "lawyerService")
public class LawyerServiceImpl extends BaseServiceImpl<Lawyer, String> implements LawyerService {
    @Autowired
    private LawyerMapper lawyerMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionReplyService questionReplyService;

    @Override
    public BaseMapper getBaseMapper() {
        return lawyerMapper;
    }

    @Override
    public ResultBean create(Lawyer lawyer) {
        lawyer.setLawyerId(UUID.randomUUID().toString());
        lawyerMapper.save(lawyer);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean getQuestionList(LawyerVo lawyerVo) {
        Lawyer lawyer = get(lawyerVo.getLawyerId());
        List<QuestionReply> questionReplies = questionReplyService.getReplyByLawyer(lawyer.getLawyerId());
        List<String> questionIds = new ArrayList<>();
        for (QuestionReply questionReply : questionReplies) {
            String questionId = questionReply.getQuestionId();
            questionIds.add(questionId);
        }
        List<Question> questions = questionService.getMany(questionIds);
        List<QuestionReplyPo> questionReplyPos = LawyerUtils.toQuestionReplyPo(lawyer, questionReplies, questions);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, questionReplyPos);
    }
    @Override
    public ResultBean randomSelect(){
        List<Lawyer> lawyers = lawyerMapper.randomSelect();
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawyers);
    }
}
