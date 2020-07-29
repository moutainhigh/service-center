package com.shengsu.website.market.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.entity.SystemConfig;
import com.shengsu.website.market.mapper.SystemConfigMapper;
import com.shengsu.website.market.po.SystemConfigCloudLegalPo;
import com.shengsu.website.market.po.SystemConfigConsultPo;
import com.shengsu.website.market.service.SystemConfigService;
import com.shengsu.website.market.util.SystemConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-22 14:47
 **/
@Service(value = "systemConfigService")
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, String> implements SystemConfigService {
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public BaseMapper<SystemConfig, String> getBaseMapper() {
        return systemConfigMapper;
    }

    @Override
    public ResultBean selectConsultFee() {
        SystemConfig systemConfig = systemConfigMapper.getFee();
        if(systemConfig ==null){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION);
        }
        SystemConfigConsultPo systemConfigConsultPo = SystemConfigUtils.toSystemConfigConsultPo(systemConfig);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, systemConfigConsultPo);
    }
    @Override
    public ResultBean selectCloudLegalFee() {
        SystemConfig systemConfig = systemConfigMapper.getFee();
        if(systemConfig ==null){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION);
        }
        SystemConfigCloudLegalPo systemConfigCloudLegalPo = SystemConfigUtils.toSystemConfigCloudLegalPo(systemConfig);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, systemConfigCloudLegalPo);
    }
}
