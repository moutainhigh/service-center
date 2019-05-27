package com.shengsu.log.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.log.entity.LogBusiness;
import com.shengsu.log.entity.LogOp;

public interface LogBusinessService extends BaseService<LogBusiness,String> {
    public void log(LogBusiness logBusiness);
}
