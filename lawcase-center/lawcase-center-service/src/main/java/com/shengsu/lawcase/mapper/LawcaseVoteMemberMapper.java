package com.shengsu.lawcase.mapper;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcaseGroupPermanentMember;
import com.shengsu.lawcase.entity.LawcaseJoinUser;
import com.shengsu.lawcase.entity.LawcaseVoteMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @ClassName: LawcaseVoteMemberMapper
 * @Description: 案件终审人员管理（数据访问实现类）
 */
@Mapper
public interface LawcaseVoteMemberMapper extends BaseMapper<LawcaseVoteMember, String> {

    List<String> getVoteMemberUserId();

    List<LawcaseJoinUser> getLawcaseJoinUser();

    int updateDel(LawcaseVoteMember lawcaseVoteMember);

}
