package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import com.shengsu.website.bdapp.mapper.ComplaintAppendixMapper;
import com.shengsu.website.bdapp.service.ComplaintAppendixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 17:04
 **/
@Service("complaintService")
public class ComplaintAppendixServiceImpl extends BaseServiceImpl<ComplaintAppendix, String> implements ComplaintAppendixService {
    @Autowired
    private ComplaintAppendixMapper complaintAppendixMapper;
    @Override
    public BaseMapper<ComplaintAppendix, String> getBaseMapper() {
        return complaintAppendixMapper;
    }
}
