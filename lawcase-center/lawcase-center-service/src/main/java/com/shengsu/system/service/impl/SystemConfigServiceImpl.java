package com.shengsu.system.service.impl;

import com.shengsu.lawcase.entity.LawcaseAppointMember;
import com.shengsu.lawcase.entity.LawcaseApproveTrialMember;
import com.shengsu.lawcase.entity.LawcaseGroupPermanentMember;
import com.shengsu.lawcase.entity.LawcaseJoinUser;
import com.shengsu.lawcase.mapper.LawcaseAppointMemberMapper;
import com.shengsu.lawcase.mapper.LawcaseApproveTrialMemberMapper;
import com.shengsu.lawcase.mapper.LawcaseGroupPermanentMemberMapper;
import com.shengsu.system.service.SystemConfigService;
import com.shengsu.result.ResultBean;
import com.shengsu.system.constant.ResultCode;
import com.shengsu.system.util.ResultUtil;
import com.shengsu.system.util.SystemConfigUtils;
import com.shengsu.system.vo.SystemConfigUpdateDelVo;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyc on 2019/11/15.
 */
@Service("systemConfigService")
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private LawcaseAppointMemberMapper lawcaseAppointMemberMapper;
    @Autowired
    private LawcaseApproveTrialMemberMapper lawcaseApproveTrialMemberMapper;
    @Autowired
    private LawcaseGroupPermanentMemberMapper lawcaseGroupPermanentMemberMapper;


    public ResultBean getLawcaseUser(){
        Map<String, List<LawcaseJoinUser>> resultMap = new HashMap<>();
        resultMap.put("appointMemberList",lawcaseAppointMemberMapper.getLawcaseJoinUser());
        resultMap.put("approveTrialMemberList",lawcaseGroupPermanentMemberMapper.getLawcaseJoinUser());
        resultMap.put("groupPermanentMemberList",lawcaseApproveTrialMemberMapper.getLawcaseJoinUser());
        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
    }

    @Override
    public ResultBean updateDel(SystemConfigUpdateDelVo systemConfigUpdateDelVo) {
        Integer type = systemConfigUpdateDelVo.getType();
        switch (type){
            case 1:
                LawcaseAppointMember appointMember = SystemConfigUtils.toLawcaseAppointMember(systemConfigUpdateDelVo);
                lawcaseAppointMemberMapper.updateDel(appointMember);
                break;
            case 2:
                LawcaseApproveTrialMember approveTrialMember = SystemConfigUtils.toLawcaseApproveTrialMember(systemConfigUpdateDelVo);
                lawcaseApproveTrialMemberMapper.updateDel(approveTrialMember);
                break;
            case 3:
                LawcaseGroupPermanentMember groupPermanentMember = SystemConfigUtils.toLawcaseGroupPermanentMember(systemConfigUpdateDelVo);
                lawcaseGroupPermanentMemberMapper.updateDel(groupPermanentMember);
                break;

        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }


}
