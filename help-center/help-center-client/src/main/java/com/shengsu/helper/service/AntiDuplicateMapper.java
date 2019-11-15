package com.shengsu.helper.service;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.helper.entity.AntiDuplicate;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zxh
 * @ClassName: AntiDuplicateMapper
 * @Description: 防重复Mapper
 * @date 2018-10-30
 */
@Mapper
public interface AntiDuplicateMapper extends BaseMapper<AntiDuplicate, String> {

}
