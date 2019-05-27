package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.mapper.LogBusinessMapper;
import com.shengsu.log.service.LogBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("logBusinessService")
public class LogBusinessServiceImpl extends BaseServiceImpl<LogBusiness,String>  implements LogBusinessService {
	@Autowired
    LogBusinessMapper logBusinessMapper;

	@Override
	public BaseMapper<LogBusiness, String> getBaseMapper() {
		return logBusinessMapper;
	}
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(LogBusiness logBusiness)
    {
        logBusinessMapper.save(logBusiness);
    }
}
