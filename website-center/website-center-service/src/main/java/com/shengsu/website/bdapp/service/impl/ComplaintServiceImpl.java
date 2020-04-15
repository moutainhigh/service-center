package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.bdapp.entity.Complaint;
import com.shengsu.website.bdapp.entity.Feedback;
import com.shengsu.website.bdapp.mapper.ComplaintMapper;
import com.shengsu.website.bdapp.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 17:04
 **/
@Service("complaintService")
public class ComplaintServiceImpl extends BaseServiceImpl<Complaint, String> implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;
    @Override
    public BaseMapper<Complaint, String> getBaseMapper() {
        return complaintMapper;
    }
}
