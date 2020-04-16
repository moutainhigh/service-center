package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import com.shengsu.website.bdapp.vo.ComplaintAppendixDetailsVo;

public interface ComplaintAppendixService extends BaseService<ComplaintAppendix,String> {
    ResultBean getDetails(ComplaintAppendixDetailsVo ossUrlVo);

    void deleteByComplaintId(String complaintId);
}
