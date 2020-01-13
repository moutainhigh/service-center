package com.shengsu.any.system.service;

import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.util.List;

public interface SystemDictService extends BaseService<SystemDict,String> {
    ResultBean listDicts(List<SystemDict> systemDicts);
}
