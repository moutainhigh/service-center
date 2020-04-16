package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.Complaint;
import com.shengsu.website.bdapp.vo.ComplaintCreateVo;

public interface ComplaintService extends BaseService<Complaint,String> {
    ResultBean create(ComplaintCreateVo complaintCreateVo);
}
