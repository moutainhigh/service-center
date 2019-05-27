package com.shengsu.log.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.log.entity.LogError;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: LogErrorDaoImpl
 * @Description: 错误日志
 * @author zxh
 * @date 2018-9-10
 * 
 */
@Mapper
public interface LogErrorMapper extends BaseMapper<LogError, String>
{
}
