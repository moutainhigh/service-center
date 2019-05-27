package com.shengsu.log.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.log.entity.LogOp;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: LogOpDaoImpl
 * @Description: 操作日志
 * @author zxh
 * @date 2018-9-10
 *
 */
@Mapper
public interface LogOpMapper extends BaseMapper<LogOp, String>
{
}