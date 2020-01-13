package com.shengsu.any.system.service;

import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.util.List;
import java.util.Map;

public interface SystemDictService extends BaseService<SystemDict,String> {
    ResultBean listDicts(List<SystemDict> systemDicts);
    List<SystemDict> getManyByDisplayVlue(Map<String, Object> map);
    SystemDict getOneByDisplayVlue(String dictCode,String displayValue);
    List<SystemDict> listByDictCode(String dictCode);
}
