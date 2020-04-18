package com.shengsu.website.home.service;


import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.home.po.ConsultAppendixDetailsPo;

import java.util.List;

public interface LawcaseConsultAppendixService extends BaseService {

    int deleteByRefId(String receivableId);

    ResultBean<List<ConsultAppendixDetailsPo>> getDetails(String refId);
    ResultBean<List<ConsultAppendixDetailsPo>> getDetailsListByPage(List<String> refIds);
}
