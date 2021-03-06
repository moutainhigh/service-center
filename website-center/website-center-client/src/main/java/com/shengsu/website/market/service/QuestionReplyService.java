package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.entity.QuestionReply;
import com.shengsu.website.market.po.QuestionReplyPo;
import com.shengsu.website.market.vo.SystemTagVo;

import java.util.List;

public interface QuestionReplyService extends BaseService<QuestionReply,String> {
    ResultBean create(QuestionReply questionReply);
    ResultBean selectAll();
    List<QuestionReply> getReplyByLawyer(String lawyerId);
    ResultBean randomThree(SystemTagVo systemTagVo);
    ResultBean questionReplyListByPage(Question question);
    void geturls(List<Lawyer> lawyers);

    ResultBean getQuestionReplyList(String systemTag,Lawyer lawyer);
}
