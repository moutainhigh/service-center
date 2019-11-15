package com.shengsu.lawcase.mapper;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcaseAppointMember;
import com.shengsu.lawcase.entity.LawcaseJoinUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @ClassName: LawcaseAppointMemberMapper
 * @Description: 案件指派人管理（数据访问实现类）
 */
@Mapper
public interface LawcaseAppointMemberMapper extends BaseMapper<LawcaseAppointMember, String> {

    List<String> getAppointMemberUserId();

    List<LawcaseJoinUser>  getLawcaseJoinUser();

    int updateDel(LawcaseAppointMember lawcaseAppointMember);

}
