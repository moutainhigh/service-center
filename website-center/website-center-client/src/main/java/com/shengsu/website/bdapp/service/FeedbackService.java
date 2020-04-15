package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.Feedback;
import com.shengsu.website.bdapp.vo.FeedbackCreateVo;
import com.shengsu.website.bdapp.vo.FeedbackListPageVo;

public interface FeedbackService extends BaseService<Feedback,String> {
    ResultBean create(FeedbackCreateVo feedbackCreateVo);
    ResultBean listPage(FeedbackListPageVo feedbackListPageVo);
}
