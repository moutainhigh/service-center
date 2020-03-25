package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.mapper.LogBusinessMapper;
import com.shengsu.log.service.LogBusinessService;
import com.shengsu.mq.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("logBusinessService")
public class LogBusinessServiceImpl extends BaseServiceImpl<LogBusiness,String>  implements LogBusinessService,MessageProcessor<LogBusiness> {
	@Autowired
    LogBusinessMapper logBusinessMapper;

	@Override
	public BaseMapper<LogBusiness, String> getBaseMapper() {
		return logBusinessMapper;
	}
    public void log(LogBusiness logBusiness)
    {
        logBusinessMapper.save(logBusiness);
    }

    @Override
    public boolean handleMessage(LogBusiness logBusiness) {
        log.info("处理消息："+ logBusiness);
        logBusinessMapper.save(logBusiness);
        return true;
    }

    @Override
    public Class<LogBusiness> getClazz() {
        return LogBusiness.class;
    }
}
