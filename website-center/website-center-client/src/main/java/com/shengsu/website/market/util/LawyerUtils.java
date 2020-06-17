package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.entity.QuestionReply;
import com.shengsu.website.market.po.LawyerPo;
import com.shengsu.website.market.po.QuestionReplyPo;

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
        for (Question question : questions) {
            QuestionReplyPo questionReplyPo = new QuestionReplyPo();
            questionReplyPo.setQuestionContent(question.getQuestionContent());
            questionReplyPo.setQuestionId(question.getQuestionId());
            questionReplyPos.add(questionReplyPo);
        }
        for (QuestionReplyPo questionReplyPo : questionReplyPos) {
            for (QuestionReply questionReply : questionReplies) {
                    if (questionReplyPo.getQuestionId().equals(questionReply.getQuestionId()) && questionReply.getReplyLawyerId().equals(lawyer.getLawyerId())) {
                        questionReplyPo.setLawyerId(lawyer.getLawyerId());
                        questionReplyPo.setLawyerName(lawyer.getLawyerName());
                        questionReplyPo.setIconOssResourceUrl(lawyer.getIconOssResourceId());
                        questionReplyPo.setReplyId(questionReply.getReplyId());
                        questionReplyPo.setReplyContent(questionReply.getReplyContent());
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
            lawyerPo.setConsultFee(lawyer.getConsultFee());
            lawyerPo.setRank(lawyer.getRank());
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
        lawyerPo.setRank(lawyer.getRank());
        lawyerPo.setConsultFee(lawyer.getConsultFee());
        return lawyerPo;
    }
}
