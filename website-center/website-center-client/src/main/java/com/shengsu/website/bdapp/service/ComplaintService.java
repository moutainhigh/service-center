package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.Complaint;
import com.shengsu.website.bdapp.vo.ComplaintCreateVo;
import com.shengsu.website.bdapp.vo.ComplaintListPageVo;

public interface ComplaintService extends BaseService<Complaint,String> {
    ResultBean create(ComplaintCreateVo complaintCreateVo);
    ResultBean listPage(ComplaintListPageVo complaintListPageVo);
    ResultBean deleteComplaint(String complaintId);
}
