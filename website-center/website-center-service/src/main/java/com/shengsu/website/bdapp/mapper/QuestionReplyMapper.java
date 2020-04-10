package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.bdapp.entity.QuestionReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionReplyMapper extends BaseMapper<QuestionReply,String> {
    List<QuestionReply> getReplyByLawyer(String lawyerId);
}
