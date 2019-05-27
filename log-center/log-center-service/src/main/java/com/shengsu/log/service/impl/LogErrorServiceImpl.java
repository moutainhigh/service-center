package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.log.entity.LogError;
import com.shengsu.log.mapper.LogErrorMapper;
import com.shengsu.log.service.LogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: LogErrorServiceImpl
 * @Description: 错误日志
 * @author zxh
 * @date 2018-9-10
 * 
 */
@Service("logErrorService")
public class LogErrorServiceImpl extends BaseServiceImpl<LogError, String> implements LogErrorService
{

    /**
     * 错误日志数据访问对象
     */
    @Autowired
    private LogErrorMapper logErrorMapper;

    @Override
    public BaseMapper<LogError, String> getBaseMapper() {
        return logErrorMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(LogError logError)
    {
        logErrorMapper.save(logError);
    }

}