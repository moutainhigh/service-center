package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.log.entity.LogOp;
import com.shengsu.log.mapper.LogOpMapper;
import com.shengsu.log.service.LogOpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: LogOpServiceImpl
 * @Description: 操作日志
 * @author zxh
 * @date 2018-9-10
 * 
 */
@Service("logOpService")
public class LogOpServiceImpl extends BaseServiceImpl<LogOp, String> implements LogOpService
{

    /**
     * 操作日志数据访问对象
     */
    @Autowired
    private LogOpMapper logOpMapper;

    @Override
    public BaseMapper<LogOp, String> getBaseMapper() {
        return logOpMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(LogOp logOp)
    {
        logOpMapper.save(logOp);
    }

}
