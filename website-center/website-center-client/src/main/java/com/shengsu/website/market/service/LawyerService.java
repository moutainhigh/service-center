package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Lawyer;
import com.shengsu.website.market.vo.LawyerVo;
import com.shengsu.website.market.vo.SystemTagVo;

public interface LawyerService extends BaseService<Lawyer,String> {
    ResultBean create(Lawyer lawyer);
    ResultBean getLawyerQuestion(LawyerVo lawyerVo);
    ResultBean randomThree(SystemTagVo systemTagVo);
    ResultBean selectAll(SystemTagVo systemTagVo);
    ResultBean getBylawyerId(String lawyerId);
    ResultBean lawyerListByPage(Lawyer lawyer);
    ResultBean edit(Lawyer lawyer);
    ResultBean remove(LawyerVo lawyerVo);
}
