package com.shengsu.website.market.util;

import com.shengsu.util.StringUtil;
import com.shengsu.website.market.entity.Feedback;
import com.shengsu.website.market.po.FeedbackListPagePo;
import com.shengsu.website.market.vo.FeedbackCreateVo;
import com.shengsu.website.market.vo.FeedbackListPageVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 13:51
 **/
public class FeedbackUtils {
    public static Feedback toFeedback(FeedbackCreateVo feedbackCreateVo) {
        if (feedbackCreateVo != null) {
            Feedback feedback = new Feedback();
            feedback.setSuggestion(feedbackCreateVo.getSuggestion());
            feedback.setTel(feedbackCreateVo.getTel());
            return feedback;
        }
        return null;
    }
    public static Feedback toFeedback(FeedbackListPageVo feedbackListPageVo) {
        if (feedbackListPageVo != null) {
            Feedback feedback = new Feedback();
            feedback.setSuggestion(feedbackListPageVo.getSuggestion());
            feedback.setTel(feedbackListPageVo.getTel());
            feedback.setSearch(StringUtil.ToLikeStr(feedbackListPageVo.getSearch()));
            feedback.setStartTime(feedbackListPageVo.getStartTime());
            feedback.setEndTime(feedbackListPageVo.getEndTime());
            feedback.setPage(feedbackListPageVo.getPage());
            feedback.setPageSize(feedbackListPageVo.getPageSize());
            return feedback;
        }
        return null;
    }

    public static List<FeedbackListPagePo> toFeedbackListPagePos(List<Feedback> feedbacks) {
        if (feedbacks != null && !feedbacks.isEmpty()) {
            List<FeedbackListPagePo> feedbackListPagePos = new ArrayList<>();
            for (Feedback feedback : feedbacks){
                feedbackListPagePos.add(toFeedbackListPagePo(feedback));
            }
            return feedbackListPagePos;
        }
        return null;
    }

    private static FeedbackListPagePo toFeedbackListPagePo(Feedback feedback) {
        if (feedback != null) {
            FeedbackListPagePo feedbackListPagePo = new FeedbackListPagePo();
            feedbackListPagePo.setFeedbackId(feedback.getFeedbackId());
            feedbackListPagePo.setSuggestion(feedback.getSuggestion());
            feedbackListPagePo.setTel(feedback.getTel());
            feedbackListPagePo.setCreateTime(feedback.getCreateTime());
            return feedbackListPagePo;
        }
        return null;
    }
}
