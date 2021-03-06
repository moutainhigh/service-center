package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.QuestionReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionReplyMapper extends BaseMapper<QuestionReply,String> {
    List<QuestionReply> getReplyByLawyer(String lawyerId);
    List<QuestionReply> randomSelect();
    List<QuestionReply> listQuestionReply();
}
