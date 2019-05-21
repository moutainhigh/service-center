package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.Lawcase;
import com.shengsu.lawcase.entity.LawcasePerson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by lipiao on 2019/5/21.
 */

@Mapper
public interface LawcasePersonMapper extends BaseMapper<LawcasePerson,String> {

    List<LawcasePerson> getManyByCaseIds(List<String> caseIds);
}
