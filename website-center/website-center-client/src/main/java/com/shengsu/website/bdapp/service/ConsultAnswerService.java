package com.shengsu.website.bdapp.service;

import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.ConsultAnswer;
import com.shengsu.website.bdapp.entity.Question;
import com.shengsu.website.bdapp.vo.ConsultAnswerDeleteVo;
import com.shengsu.website.bdapp.vo.ConsultAnswerVo;

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
