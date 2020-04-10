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
        for (QuestionReply questionReply : questionReplies) {
            for (Lawyer lawyer : lawyers) {
                if (questionReply.getReplyLawyerId().equals(lawyer.getLawyerId())) {
                    QuestionReplyPo questionReplyPo = new QuestionReplyPo();
                    questionReplyPo.setLawyerId(lawyer.getLawyerId());
                    questionReplyPo.setLawyerName(lawyer.getLawyerName());
                    questionReplyPo.setIconOssResourceId(lawyer.getIconOssResourceId());
                    questionReplyPo.setReplyId(questionReply.getReplyId());
                    questionReplyPo.setReplyContent(questionReply.getReplyContent());
                    questionReplyPo.setQuestionId(questionReply.getQuestionId());
                    questionReplyPos.add(questionReplyPo);

                }
            }
        }
        for (QuestionReplyPo replyPo : questionReplyPos) {
            for (Question question : questions) {
                if (replyPo.getQuestionId().equals(question.getQuestionId())) {
                    replyPo.setQuestionContent(question.getQuestionContent());
                }
            }
        }
        return questionReplyPos;
    }
}
