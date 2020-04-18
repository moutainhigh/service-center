package com.shengsu.website.home.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.home.entity.LawcaseConsultAppendix;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-05 09:30
 **/
@Mapper
public interface LawcaseConsultAppendixMapper extends BaseMapper {

    int deleteByRefId(String refId);

    List<LawcaseConsultAppendix> selectByRefId(String refId);
    List<LawcaseConsultAppendix> selectByRefIdList(List<String> refIds);
}
