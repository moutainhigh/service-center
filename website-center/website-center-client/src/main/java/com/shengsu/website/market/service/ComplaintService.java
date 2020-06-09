package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.Complaint;
import com.shengsu.website.market.vo.ComplaintCreateVo;
import com.shengsu.website.market.vo.ComplaintListPageVo;

public interface ComplaintService extends BaseService<Complaint,String> {
    ResultBean create(ComplaintCreateVo complaintCreateVo);
    ResultBean listPage(ComplaintListPageVo complaintListPageVo);
    ResultBean deleteComplaint(String complaintId);
}
