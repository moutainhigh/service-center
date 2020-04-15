package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.bdapp.entity.Feedback;
import com.shengsu.website.bdapp.mapper.FeedbackMapper;
import com.shengsu.website.bdapp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 11:42
 **/
@Service("feedbackService")
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback, String> implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public BaseMapper<Feedback, String> getBaseMapper() {
        return feedbackMapper;
    }
}
