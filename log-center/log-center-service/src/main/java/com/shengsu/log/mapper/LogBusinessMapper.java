package com.shengsu.log.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.log.entity.LogBusiness;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 业务日志
 * @author zxh
 * @date 2018-9-10
 *
 */
@Mapper
public interface LogBusinessMapper extends BaseMapper<LogBusiness,String> {
}
