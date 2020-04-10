package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.QuestionReply;

import java.util.List;

public interface QuestionReplyService extends BaseService<QuestionReply,String> {
    ResultBean create(QuestionReply questionReply);
    ResultBean selectAll();
    List<QuestionReply> getReplyByLawyer(String lawyerId);
    ResultBean randomSelect();
}
