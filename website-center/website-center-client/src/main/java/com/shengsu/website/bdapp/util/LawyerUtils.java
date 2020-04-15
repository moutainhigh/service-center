package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.entity.QuestionReply;
import com.shengsu.website.bdapp.po.LawyerPo;
import com.shengsu.website.bdapp.po.QuestionReplyPo;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 16:58
 **/
public class LawyerUtils {
    public static List<QuestionReplyPo> toQuestionReplyPo(Lawyer lawyer, List<QuestionReply> questionReplies, List<Question> questions) {
        List<QuestionReplyPo> questionReplyPos = new ArrayList<>();
        for (QuestionReply questionReply : questionReplies) {
            QuestionReplyPo questionReplyPo = new QuestionReplyPo();
            questionReplyPo.setLawyerId(lawyer.getLawyerId());
            questionReplyPo.setLawyerName(lawyer.getLawyerName());
            questionReplyPo.setIconOssResourceUrl(lawyer.getIconOssResourceId());
            questionReplyPo.setReplyId(questionReply.getReplyId());
            questionReplyPo.setReplyContent(questionReply.getReplyContent());
            questionReplyPo.setQuestionId(questionReply.getQuestionId());
            questionReplyPos.add(questionReplyPo);
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
    public static List<LawyerPo> toLawyerPos(List<Lawyer> lawyers){
        List<LawyerPo> lawyerPos = new ArrayList<>();
        for(Lawyer lawyer : lawyers){
            LawyerPo lawyerPo = new LawyerPo();
            lawyerPo.setLawyerId(lawyer.getLawyerId());
            lawyerPo.setLawyerName(lawyer.getLawyerName());
            lawyerPo.setIconOssResourceUrl(lawyer.getIconOssResourceId());
            lawyerPo.setField(lawyer.getField());
            lawyerPo.setCreateTime(lawyer.getCreateTime());
            lawyerPo.setModifyTime(lawyer.getModifyTime());
            lawyerPo.setConsultTimes(lawyer.getConsultTimes());
            lawyerPo.setPraiseTimes(lawyer.getPraiseTimes());
            lawyerPos.add(lawyerPo);
        }
        return lawyerPos;
    }
    public static LawyerPo toLawyerPo(Lawyer lawyer){
        LawyerPo lawyerPo = new LawyerPo();
        lawyerPo.setLawyerId(lawyer.getLawyerId());
        lawyerPo.setLawyerName(lawyer.getLawyerName());
        lawyerPo.setIconOssResourceUrl(lawyer.getIconOssResourceId());
        lawyerPo.setField(lawyer.getField());
        lawyerPo.setCreateTime(lawyer.getCreateTime());
        lawyerPo.setModifyTime(lawyer.getModifyTime());
        lawyerPo.setConsultTimes(lawyer.getConsultTimes());
        lawyerPo.setPraiseTimes(lawyer.getPraiseTimes());
        return lawyerPo;
    }
}
