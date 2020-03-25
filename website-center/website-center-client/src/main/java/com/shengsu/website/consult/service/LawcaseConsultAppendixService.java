package com.shengsu.website.consult.service;


import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.consult.po.ConsultAppendixDetailsPo;

import java.util.List;

public interface LawcaseConsultAppendixService extends BaseService {

    int deleteByRefId(String receivableId);

    ResultBean<List<ConsultAppendixDetailsPo>> getDetails(String refId);
    ResultBean<List<ConsultAppendixDetailsPo>> getDetailsListByPage(List<String> refIds);
}
