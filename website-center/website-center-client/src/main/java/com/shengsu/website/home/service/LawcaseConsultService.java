package com.shengsu.website.home.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.home.entity.LawcaseConsult;

public interface LawcaseConsultService extends BaseService {
    /**
     * 构造咨询内容
     *
     * @param
     */
    void create(LawcaseConsult lawcaseConsult);

    ResultBean listByPage(LawcaseConsult lawcaseConsult);

    ResultBean listByModel(LawcaseConsult lawcaseConsult);

}
