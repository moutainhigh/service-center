package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.Lawyer;
import com.shengsu.website.bdapp.vo.LawyerVo;

public interface LawyerService extends BaseService<Lawyer,String> {
    ResultBean create(Lawyer lawyer);
    ResultBean getQuestionList(LawyerVo lawyerVo);
    ResultBean randomSelect();
}
