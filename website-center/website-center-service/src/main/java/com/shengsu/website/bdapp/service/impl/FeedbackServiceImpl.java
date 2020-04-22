package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.bdapp.entity.Feedback;
import com.shengsu.website.bdapp.mapper.FeedbackMapper;
import com.shengsu.website.bdapp.po.FeedbackListPagePo;
import com.shengsu.website.bdapp.service.FeedbackService;
import com.shengsu.website.bdapp.util.FeedbackUtils;
import com.shengsu.website.bdapp.vo.FeedbackCreateVo;
import com.shengsu.website.bdapp.vo.FeedbackListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean create(FeedbackCreateVo feedbackCreateVo) {
        Feedback feedback = FeedbackUtils.toFeedback(feedbackCreateVo);
        feedback.setFeedbackId(UUID.randomUUID().toString());
        save(feedback);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean listPage(FeedbackListPageVo feedbackListPageVo) {
        Feedback feedback = FeedbackUtils.toFeedback(feedbackListPageVo);
        Integer count = feedbackMapper.countAll(feedback);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<Feedback> feedbacks = feedbackMapper.listByPage(feedback);
            if(null == feedbacks || feedbacks.size() == 0){
                return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
            }
            List<FeedbackListPagePo> feedbackListPagePos = FeedbackUtils.toFeedbackListPagePos(feedbacks);
            resultMap.put(CommonConst.ROOT, feedbackListPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean deleteFeedback(String feedbackId) {
        softDelete(feedbackId);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

}
