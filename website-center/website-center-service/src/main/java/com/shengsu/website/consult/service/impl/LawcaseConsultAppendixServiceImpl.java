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

import java.util.List;


/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-05 09:26
 **/
@Service(value = "lawcaseFinanceAppendixService")
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
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_FINANCE_APPENDIX_REFID_IS_NULL);
        }

        List<ConsultAppendixDetailsPo> appendixDetailsPos = LawcaseConsultAppendixUtils.toFinanceAppendixDetailsPos(appendixList);
        getDetaIlsOssUrl(appendixDetailsPos);
        return ResultUtil.formResult(true,ResultCode.SUCCESS,appendixDetailsPos);
    }
    @Override
    public ResultBean<List<ConsultAppendixDetailsPo>> getDetailsListByPage(List<String> refIds){
        List<LawcaseConsultAppendix> appendixList = lawcaseConsultAppendixMapper.selectByRefIdList(refIds);
        if(appendixList==null||appendixList.isEmpty()){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_FINANCE_APPENDIX_REFID_IS_NULL);
        }

        List<ConsultAppendixDetailsPo> appendixDetailsPos = LawcaseConsultAppendixUtils.toFinanceAppendixDetailsPos(appendixList);
        getDetaIlsOssUrl(appendixDetailsPos);
        return ResultUtil.formResult(true,ResultCode.SUCCESS,appendixDetailsPos);
    }
    private void getDetaIlsOssUrl(List<ConsultAppendixDetailsPo> appendixDetailsPos){
        for (ConsultAppendixDetailsPo appendix:
        appendixDetailsPos) {
            String url = ossService.getUrl(OssConstant.OSS_LAWYER_PLATFORM_FFILEDIR, appendix.getOssResourceId());
            appendix.setOssResourceUrl(url);
        }
    }
}
