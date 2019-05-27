package com.shengsu.log.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.log.entity.LogOp;

public interface LogOpService extends BaseService<LogOp,String> {
    public void log(LogOp logOp);
}
