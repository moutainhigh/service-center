package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.ComplaintAppendix;
import com.shengsu.website.market.vo.ComplaintAppendixDetailsVo;

public interface ComplaintAppendixService extends BaseService<ComplaintAppendix,String> {
    ResultBean getDetails(ComplaintAppendixDetailsVo ossUrlVo);

    void deleteByComplaintId(String complaintId);
}
