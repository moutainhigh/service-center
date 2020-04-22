package com.shengsu.system.service.impl;

import com.shengsu.lawcase.entity.*;
import com.shengsu.lawcase.mapper.LawcaseAppointMemberMapper;
import com.shengsu.lawcase.mapper.LawcaseApproveTrialMemberMapper;
import com.shengsu.lawcase.mapper.LawcaseGroupPermanentMemberMapper;
import com.shengsu.lawcase.mapper.LawcaseVoteMemberMapper;
import com.shengsu.result.ResultUtil;
import com.shengsu.system.service.SystemConfigService;
import com.shengsu.result.ResultBean;
import com.shengsu.app.contant.ResultCode;
import com.shengsu.system.util.SystemConfigUtils;
import com.shengsu.system.vo.SystemConfigUpdateDelVo;
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
    @Autowired
    private LawcaseVoteMemberMapper lawcaseVoteMemberMapper;


    public ResultBean getLawcaseUser(){
        Map<String, List<LawcaseJoinUser>> resultMap = new HashMap<>();
        resultMap.put("appointMemberList",lawcaseAppointMemberMapper.getLawcaseJoinUser());
        resultMap.put("approveTrialMemberList",lawcaseApproveTrialMemberMapper.getLawcaseJoinUser());
        resultMap.put("groupPermanentMemberList",lawcaseGroupPermanentMemberMapper.getLawcaseJoinUser());
        resultMap.put("voteMemberList",lawcaseVoteMemberMapper.getLawcaseJoinUser());
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
            case 4:
                LawcaseVoteMember voteMember = SystemConfigUtils.toLawcaseVoteMember(systemConfigUpdateDelVo);
                lawcaseVoteMemberMapper.updateDel(voteMember);
                break;

        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }


}
