package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcaseUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lipiao on 2019/5/21.
 */

@Mapper
public interface LawcaseUserMapper extends BaseMapper<LawcaseUser,String> {
}
