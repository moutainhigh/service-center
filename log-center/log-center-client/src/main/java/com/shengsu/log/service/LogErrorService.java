package com.shengsu.log.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.log.entity.LogError;

public interface LogErrorService extends BaseService<LogError,String> {
    public void log(LogError logError);
}
