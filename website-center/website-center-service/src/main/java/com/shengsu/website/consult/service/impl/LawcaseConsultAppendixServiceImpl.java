package com.shengsu.website.consult.service.impl;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.consult.entity.LawcaseConsultAppendix;
import com.shengsu.website.consult.mapper.LawcaseConsultAppendixMapper;
import com.shengsu.website.consult.po.ConsultAppendixDetailsPo;
import com.shengsu.website.consult.service.LawcaseConsultAppendixService;
import com.shengsu.website.consult.util.LawcaseConsultAppendixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-05 09:26
 **/
@Service(value = "lawcaseConsultAppendixService")
public class LawcaseConsultAppendixServiceImpl extends BaseServiceImpl implements LawcaseConsultAppendixService {
    @Autowired
    private LawcaseConsultAppendixMapper lawcaseConsultAppendixMapper;
    @Autowired
    private OssService ossService;

    @Override
    public BaseMapper<LawcaseConsultAppendix, String> getBaseMapper() {
        return lawcaseConsultAppendixMapper;
    }

    @Override
    public int deleteByRefId(String receivableId) {
        return lawcaseConsultAppendixMapper.deleteByRefId(receivableId);
    }

    @Override
    public ResultBean<List<ConsultAppendixDetailsPo>> getDetails(String refId){
        List<LawcaseConsultAppendix> appendixList = lawcaseConsultAppendixMapper.selectByRefId(refId);
        if(appendixList==null||appendixList.isEmpty()){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CONSULT_APPENDIX_REFID_IS_NULL);
        }

        List<ConsultAppendixDetailsPo> appendixDetailsPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsPos(appendixList);
        getDetaIlsOssUrl(appendixDetailsPos);
        return ResultUtil.formResult(true,ResultCode.SUCCESS,appendixDetailsPos);
    }
    @Override
    public ResultBean<List<ConsultAppendixDetailsPo>> getDetailsListByPage(List<String> refIds){
        List<LawcaseConsultAppendix> appendixList = lawcaseConsultAppendixMapper.selectByRefIdList(refIds);
        if(appendixList==null||appendixList.isEmpty()){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CONSULT_APPENDIX_REFID_IS_NULL);
        }

        List<ConsultAppendixDetailsPo> appendixDetailsPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsPos(appendixList);
        getDetaIlsOssUrl(appendixDetailsPos);
        return ResultUtil.formResult(true,ResultCode.SUCCESS,appendixDetailsPos);
    }
    private void getDetaIlsOssUrl(List<ConsultAppendixDetailsPo> appendixDetailsPos){
        List<String> list = new ArrayList<>();
        for (ConsultAppendixDetailsPo appendix:
        appendixDetailsPos) {
            String url = appendix.getOssResourceId();
            list.add(url);
        }
        Map<String, String>  map = ossService.getUrls(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,list);
            for(ConsultAppendixDetailsPo appendix:
                    appendixDetailsPos){
                    appendix.setOssResourceUrl(map.get(appendix.getOssResourceId()));
            }
    }
}
