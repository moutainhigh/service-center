package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Feedback;
import com.shengsu.website.market.vo.FeedbackCreateVo;
import com.shengsu.website.market.vo.FeedbackListPageVo;

public interface FeedbackService extends BaseService<Feedback,String> {
    ResultBean create(FeedbackCreateVo feedbackCreateVo);
    ResultBean listPage(FeedbackListPageVo feedbackListPageVo);
    ResultBean deleteFeedback(String feedbackId);
}
