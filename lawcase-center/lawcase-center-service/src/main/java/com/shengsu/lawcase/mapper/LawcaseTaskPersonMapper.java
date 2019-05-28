package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcaseTaskPerson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Created by lipiao on 2019/5/21.
 */

@Mapper
public interface LawcaseTaskPersonMapper extends BaseMapper<LawcaseTaskPerson,String> {

    List<LawcaseTaskPerson> getManyByCaseIds(List<String> caseIds);
}
