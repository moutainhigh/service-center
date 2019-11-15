package com.shengsu.lawcase.mapper;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcaseAppointMember;
import com.shengsu.lawcase.entity.LawcaseGroupPermanentMember;
import com.shengsu.lawcase.entity.LawcaseJoinUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxh
 * @ClassName: LawcaseGroupMapper
 * @Description: 案件组管理（数据访问实现类）
 * @date 2018-12-24
 */
@Mapper
public interface LawcaseGroupPermanentMemberMapper extends BaseMapper<LawcaseGroupPermanentMember, String> {

    List<String> getPermanentMemberUserIds();

    List<LawcaseJoinUser>  getLawcaseJoinUser();

    int updateDel(LawcaseGroupPermanentMember lawcaseGroupPermanentMember);


}
