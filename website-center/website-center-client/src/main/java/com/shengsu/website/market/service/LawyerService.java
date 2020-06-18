package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.vo.LawyerVo;

public interface LawyerService extends BaseService<Lawyer,String> {
    ResultBean create(Lawyer lawyer);
    ResultBean getQuestionList(LawyerVo lawyerVo);
    ResultBean randomThree();
    ResultBean selectAll();
    ResultBean getBylawyerId(String lawyerId);
    ResultBean lawyerListByPage(Lawyer lawyer);
    ResultBean edit(Lawyer lawyer);
    ResultBean remove(LawyerVo lawyerVo);
}
