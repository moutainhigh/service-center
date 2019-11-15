package com.shengsu.system.util;

import com.shengsu.lawcase.entity.LawcaseAppointMember;
import com.shengsu.lawcase.entity.LawcaseApproveTrialMember;
import com.shengsu.lawcase.entity.LawcaseGroupPermanentMember;
import com.shengsu.system.vo.SystemConfigUpdateDelVo;

/**
 * Created by zyc on 2019/11/15.
 */
public class SystemConfigUtils {

    public static LawcaseAppointMember toLawcaseAppointMember(SystemConfigUpdateDelVo systemConfigUpdateDelVo){
        if (systemConfigUpdateDelVo != null) {
            LawcaseAppointMember appointMember = new LawcaseAppointMember();
            appointMember.setUserId(systemConfigUpdateDelVo.getUserId());
            appointMember.setDelFlag(systemConfigUpdateDelVo.getDelFlag());
            return appointMember;
        }
        return null;
    }

    public static LawcaseApproveTrialMember toLawcaseApproveTrialMember(SystemConfigUpdateDelVo systemConfigUpdateDelVo){
        if (systemConfigUpdateDelVo != null) {
            LawcaseApproveTrialMember appointMember = new LawcaseApproveTrialMember();
            appointMember.setLawyerUserId(systemConfigUpdateDelVo.getUserId());
            appointMember.setDelFlag(systemConfigUpdateDelVo.getDelFlag());
            return appointMember;
        }
        return null;
    }

    public static LawcaseGroupPermanentMember toLawcaseGroupPermanentMember(SystemConfigUpdateDelVo systemConfigUpdateDelVo){
        if (systemConfigUpdateDelVo != null) {
            LawcaseGroupPermanentMember appointMember = new LawcaseGroupPermanentMember();
            appointMember.setUserId(systemConfigUpdateDelVo.getUserId());
            appointMember.setDelFlag(systemConfigUpdateDelVo.getDelFlag());
            return appointMember;
        }
        return null;
    }
}
