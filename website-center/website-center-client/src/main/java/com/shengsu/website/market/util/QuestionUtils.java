package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.ConsultAnswer;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.entity.QuestionReply;
import com.shengsu.website.market.po.QuestionReplyPo;
import com.shengsu.website.market.vo.ConsultAnswerVo;

import java.util.UUID;

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
        questionReplyPo.setIconOssResourceUrl(lawyer.getIconOssResourceId());
        questionReplyPo.setQuestionId(question.getQuestionId());
        questionReplyPo.setQuestionContent(question.getQuestionContent());
        questionReplyPo.setReplyContent(questionReply.getReplyContent());
        questionReplyPo.setReplyId(questionReply.getReplyId());
        return questionReplyPo;
    }

    public static Question toQuestion(ConsultAnswer consultAnswer) {
        Question question = new Question();
        question.setQuestionId(UUID.randomUUID().toString());
        question.setQuestionContent(consultAnswer.getQuestionContent());
        return question;
    }
    public static Question toQuestion(ConsultAnswerVo consultAnswerVo) {
        Question question = new Question();
        question.setQuestionId(consultAnswerVo.getQuestionId());
        question.setQuestionContent(consultAnswerVo.getQuestionContent());
        return question;
    }
}
