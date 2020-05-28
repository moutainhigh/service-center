package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.mapper.LawyerMapper;
import com.shengsu.website.bdapp.po.LawyerPo;
import com.shengsu.website.bdapp.po.QuestionReplyPo;
import com.shengsu.website.bdapp.service.LawyerService;
import com.shengsu.website.bdapp.service.QuestionReplyService;
import com.shengsu.website.bdapp.service.QuestionService;
import com.shengsu.website.bdapp.util.LawyerUtils;
import com.shengsu.website.bdapp.vo.LawyerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private OssService ossService;

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
        if(lawyer == null){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
        }
        String url = ossService.getUrl(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR, lawyer.getIconOssResourceId());
        lawyer.setIconOssResourceId(url);
        List<QuestionReply> questionReplies = questionReplyService.getReplyByLawyer(lawyer.getLawyerId());
        if(questionReplies.isEmpty()){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, questionReplies);
        }
        List<String> questionIds = new ArrayList<>();
        for (QuestionReply questionReply : questionReplies) {
            String questionId = questionReply.getQuestionId();
            questionIds.add(questionId);
        }
        int totalCount = questionService.countAllByQuestionIds(questionIds);
        Map<String, Object> resultMap = new HashMap<>();
        List<Question> questions = questionService.getMany(questionIds);
        List<QuestionReplyPo> questionReplyPos = LawyerUtils.toQuestionReplyPo(lawyer, questionReplies, questions);
        resultMap.put(CommonConst.ROOT, questionReplyPos);
        resultMap.put(CommonConst.TOTAL_COUNT, totalCount);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
    @Override
    public ResultBean randomThree(){
        List<Lawyer> lawyers = lawyerMapper.randomSelect();
        if(lawyers == null || lawyers.size() == 0){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
        }
        questionReplyService.geturls(lawyers);
        List<LawyerPo> lawyerPos = LawyerUtils.toLawyerPos(lawyers);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawyerPos);
    }
    @Override
    public ResultBean selectAll(){
        List<Lawyer> lawyers = lawyerMapper.listAll();
        questionReplyService.geturls(lawyers);
        List<LawyerPo> lawyerPos = LawyerUtils.toLawyerPos(lawyers);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawyerPos);
    }
    @Override
    public ResultBean getBylawyerId(String lawyerId){
        Lawyer lawyer = lawyerMapper.get(lawyerId);
        if(lawyer == null){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, lawyer);
        }
        String url = ossService.getUrl(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR, lawyer.getIconOssResourceId());
        lawyer.setIconOssResourceId(url);
        LawyerPo lawyerPo = LawyerUtils.toLawyerPo(lawyer);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawyerPo);
    }
    @Override
    public ResultBean lawyerListByPage(Lawyer lawyer){
        List<Lawyer> lawyers = lawyerMapper.listByPage(lawyer);
        questionReplyService.geturls(lawyers);
        List<LawyerPo> lawyerPos = LawyerUtils.toLawyerPos(lawyers);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawyerPos);
    }
}
