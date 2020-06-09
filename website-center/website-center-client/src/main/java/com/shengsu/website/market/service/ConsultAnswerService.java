package com.shengsu.website.market.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.ConsultAnswer;
import com.shengsu.website.market.entity.Question;
import com.shengsu.website.market.vo.ConsultAnswerDeleteVo;
import com.shengsu.website.market.vo.ConsultAnswerVo;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-11 13:57
 **/
public interface ConsultAnswerService {
    ResultBean create(ConsultAnswer consultAnswer);
    ResultBean update(ConsultAnswerVo consultAnswerVo);
    ResultBean delete(ConsultAnswerDeleteVo consultAnswerDeleteVo);
    ResultBean questionReplyListByPage(Question question);
}
