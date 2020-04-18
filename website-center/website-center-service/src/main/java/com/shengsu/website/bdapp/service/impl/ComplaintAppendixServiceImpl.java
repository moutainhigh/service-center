package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import com.shengsu.website.bdapp.mapper.ComplaintAppendixMapper;
import com.shengsu.website.bdapp.po.ComplaintAppendixSimplePo;
import com.shengsu.website.bdapp.service.ComplaintAppendixService;
import com.shengsu.website.bdapp.util.ComplaintAppendixUtils;
import com.shengsu.website.bdapp.vo.ComplaintAppendixDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<String> ossResourceIds = new ArrayList<>();
        for (ComplaintAppendix appendix :complaintAppendices ){
            ossResourceIds.add(appendix.getOssResourceId());
        }
        Map<String, String> ossIdMap = ossService.getUrls(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,ossResourceIds);
        List<ComplaintAppendixSimplePo> complaintAppendixSimplePos = ComplaintAppendixUtils.toComplaintAppendixSimplePos(complaintAppendices,ossIdMap);

        return ResultUtil.formResult(true, ResultCode.SUCCESS,complaintAppendixSimplePos);
    }

    @Override
    public void deleteByComplaintId(String complaintId) {
        complaintAppendixMapper.deleteByComplaintId(complaintId);
    }
}
