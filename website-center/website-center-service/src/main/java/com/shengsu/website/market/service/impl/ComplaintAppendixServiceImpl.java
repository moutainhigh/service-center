package com.shengsu.website.market.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.home.entity.LawcaseConsult;
import com.shengsu.website.market.mapper.ComplaintAppendixMapper;
import com.shengsu.website.market.entity.ComplaintAppendix;
import com.shengsu.website.market.po.ComplaintAppendixSimplePo;
import com.shengsu.website.market.service.ComplaintAppendixService;
import com.shengsu.website.market.util.ComplaintAppendixUtils;
import com.shengsu.website.market.vo.ComplaintAppendixDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 17:04
 **/
@Service("complaintAppendixService")
public class ComplaintAppendixServiceImpl extends BaseServiceImpl<ComplaintAppendix, String> implements ComplaintAppendixService {
    @Autowired
    private OssService ossService;
    @Autowired
    private ComplaintAppendixMapper complaintAppendixMapper;
    @Override
    public BaseMapper<ComplaintAppendix, String> getBaseMapper() {
        return complaintAppendixMapper;
    }

    @Override
    public ResultBean getDetails(ComplaintAppendixDetailsVo detailsVo) {
        ComplaintAppendix complaintAppendix = ComplaintAppendixUtils.toComplaintAppendix(detailsVo);
        List<ComplaintAppendix> complaintAppendices = complaintAppendixMapper.listComplaintAppendix(complaintAppendix);
        List<String> ossResourceIds = complaintAppendices.stream().map(ComplaintAppendix::getOssResourceId).collect(Collectors.toList());
        Map<String, String> ossIdMap = ossService.getUrls(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,ossResourceIds);
        List<ComplaintAppendixSimplePo> complaintAppendixSimplePos = ComplaintAppendixUtils.toComplaintAppendixSimplePos(complaintAppendices,ossIdMap);
        return ResultUtil.formResult(true, ResultCode.SUCCESS,complaintAppendixSimplePos);
    }

    @Override
    public void deleteByComplaintId(String complaintId) {
        complaintAppendixMapper.deleteByComplaintId(complaintId);
    }
}
