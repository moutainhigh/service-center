package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.LawcasePhase;
import com.shengsu.lawcase.entity.LawcasePhaseTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Created by lipiao on 2019/5/21.
 */

@Mapper
public interface LawcasePhaseMapper extends BaseMapper<LawcasePhase,String> {

    List<LawcasePhase> getManyByCaseIds(List<String> caseIds);
}
