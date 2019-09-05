package com.shengsu.log.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.log.entity.LogBusiness;

public interface LogBusinessService extends BaseService<LogBusiness,String> {
    public void log(LogBusiness logBusiness);
}
