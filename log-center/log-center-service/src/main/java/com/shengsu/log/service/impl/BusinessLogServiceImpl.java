package com.shengsu.log.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.mapper.BusinessLogMapper;
import com.shengsu.log.service.BusinessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("businessLogService")
public class BusinessLogServiceImpl extends BaseServiceImpl<LogBusiness,String>  implements BusinessLogService {
	@Autowired
	BusinessLogMapper businessLogMapper;

	@Override
	public BaseMapper<LogBusiness, String> getBaseMapper() {
		return businessLogMapper;
	}

}
