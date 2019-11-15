package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcaseAppointMember;
import com.shengsu.lawcase.entity.LawcaseApproveTrialMember;
import com.shengsu.lawcase.entity.LawcaseJoinUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawcaseApproveTrialMemberMapper extends BaseMapper<LawcaseApproveTrialMember, String> {

    List<LawcaseJoinUser>  getLawcaseJoinUser();

    int updateDel(LawcaseApproveTrialMember lawcaseApproveTrialMember);

}
