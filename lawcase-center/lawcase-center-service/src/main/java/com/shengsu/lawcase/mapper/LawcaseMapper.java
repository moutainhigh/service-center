package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.Lawcase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zxh on 2019/5/8.
 */

@Mapper
public interface LawcaseMapper extends BaseMapper<Lawcase,String> {
    List<Lawcase> getInvestLawcaseList();
}
