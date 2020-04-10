package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.po.QuestionReplyPo;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 15:53
 **/
public class QuestionUtils {
    public static QuestionReplyPo toQuestionReplyPo(Question question, Lawyer lawyer, QuestionReply questionReply){
           QuestionReplyPo questionReplyPo = new QuestionReplyPo();
           questionReplyPo.setLawyerId(lawyer.getLawyerId());
           questionReplyPo.setLawyerName(lawyer.getLawyerName());
           questionReplyPo.setIconOssResourceId(lawyer.getIconOssResourceId());
           questionReplyPo.setQuestionId(question.getQuestionId());
           questionReplyPo.setQuestionContent(question.getQuestionContent());
           questionReplyPo.setReplyContent(questionReply.getReplyContent());
           questionReplyPo.setReplyId(questionReply.getReplyId());
           return questionReplyPo;
    }
}