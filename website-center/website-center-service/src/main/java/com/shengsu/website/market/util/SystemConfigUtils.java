package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.SystemConfig;
import com.shengsu.website.market.po.SystemConfigCloudLegalPo;
import com.shengsu.website.market.po.SystemConfigConsultPo;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-23 18:56
 **/
public class SystemConfigUtils {
    public static SystemConfigConsultPo toSystemConfigConsultPo(SystemConfig systemConfig){
        SystemConfigConsultPo systemConfigConsultPo = new SystemConfigConsultPo();
        systemConfigConsultPo.setOnlineConsultFee(systemConfig.getOnlineConsultFee());
        systemConfigConsultPo.setOnlineConsultFeeOld(systemConfig.getOnlineConsultFeeOld());
        systemConfigConsultPo.setTelConsultFee(systemConfig.getTelConsultFee());
        systemConfigConsultPo.setTelConsultFeeOld(systemConfig.getTelConsultFeeOld());
        return systemConfigConsultPo;
    }
    public static SystemConfigCloudLegalPo toSystemConfigCloudLegalPo(SystemConfig systemConfig){
        SystemConfigCloudLegalPo systemConfigCloudLegalPo = new SystemConfigCloudLegalPo();
        systemConfigCloudLegalPo.setHumanResourceFee(systemConfig.getHumanResourceFee());
        systemConfigCloudLegalPo.setHumanResourceFeeOld(systemConfig.getHumanResourceFeeOld());
        systemConfigCloudLegalPo.setContractFee(systemConfig.getContractFee());
        systemConfigCloudLegalPo.setContractFeeOld(systemConfig.getContractFeeOld());
        systemConfigCloudLegalPo.setCloudLegalFee(systemConfig.getCloudLegalFee());
        systemConfigCloudLegalPo.setCloudLegalFeeOld(systemConfig.getCloudLegalFeeOld());
        return systemConfigCloudLegalPo;
    }
}
