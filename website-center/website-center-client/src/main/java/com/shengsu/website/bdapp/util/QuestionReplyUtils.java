package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.po.QuestionReplyPo;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 14:22
 **/
public class QuestionReplyUtils {
    public static List<QuestionReplyPo> toQuestionReplyPos(List<QuestionReply> questionReplies, List<Lawyer> lawyers, List<Question> questions) {
        List<QuestionReplyPo> questionReplyPos = new ArrayList<>();
        for (Question question : questions) {
            QuestionReplyPo questionReplyPo = new QuestionReplyPo();
            questionReplyPo.setQuestionContent(question.getQuestionContent());
            questionReplyPo.setQuestionId(question.getQuestionId());
            questionReplyPos.add(questionReplyPo);
        }
        for (QuestionReplyPo questionReplyPo : questionReplyPos) {
            for (QuestionReply questionReply : questionReplies) {
                for (Lawyer lawyer : lawyers) {
                    if (questionReplyPo.getQuestionId().equals(questionReply.getQuestionId()) && questionReply.getReplyLawyerId().equals(lawyer.getLawyerId())) {
                        questionReplyPo.setLawyerId(lawyer.getLawyerId());
                        questionReplyPo.setLawyerName(lawyer.getLawyerName());
                        questionReplyPo.setIconOssResourceUrl(lawyer.getIconOssResourceId());
                        questionReplyPo.setReplyId(questionReply.getReplyId());
                        questionReplyPo.setReplyContent(questionReply.getReplyContent());
                    }
                }
            }
        }
        return questionReplyPos;
    }
}
