package com.shengsu.website.consult.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.consult.entity.LawcaseConsult;

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
