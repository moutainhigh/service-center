package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.QuestionReply;

import java.util.List;

public interface QuestionReplyService extends BaseService<QuestionReply,String> {
    ResultBean create(QuestionReply questionReply);
    ResultBean selectAll();
    List<QuestionReply> getReplyByLawyer(String lawyerId);
    ResultBean randomThree();
    ResultBean questionReplyListByPage(QuestionReply questionReply);
    void geturls(List<Lawyer> lawyers);
}
