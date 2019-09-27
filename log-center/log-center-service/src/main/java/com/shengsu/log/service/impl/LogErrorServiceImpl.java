package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.entity.LogError;
import com.shengsu.log.mapper.LogErrorMapper;
import com.shengsu.log.mq.message.MessageProcessor;
import com.shengsu.log.service.LogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zxh
 * @ClassName: LogErrorServiceImpl
 * @Description: 错误日志
 * @date 2018-9-10
 */
@Service("logErrorService")
public class LogErrorServiceImpl extends BaseServiceImpl<LogError, String> implements LogErrorService,MessageProcessor<LogError> {

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
    public void log(LogError logError) {
        logErrorMapper.save(logError);
    }

    @Override
    public boolean handleMessage(LogError logError) {
        logErrorMapper.save(logError);
        System.out.println("收到消息："+logError.toString());
        return true;
    }

    @Override
    public Class<LogError> getClazz() {
        return LogError.class;
    }
}