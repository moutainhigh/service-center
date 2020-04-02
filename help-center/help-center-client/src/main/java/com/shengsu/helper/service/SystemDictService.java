package com.shengsu.helper.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.result.ResultBean;

import java.util.List;
import java.util.Map;


public interface SystemDictService extends BaseService<SystemDict, String> {
    List<SystemDict> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDict SystemDict);

    void create(SystemDict SystemDict);

    int dictExistCheckExceptSelf(SystemDict SystemDict);

    ResultBean listDicts(List<SystemDict> systemDictionaries);

    List<SystemDict> getManyByDisplayValue(String dictCode, List<String> list);

    Map<String, SystemDict> getManyByDisplayValueMap(String dictCode, List<String> list);

    SystemDict getOneByDisplayValue(String dictCode, String displayValue);

    List<SystemDict> listByDictCode(String dictCode);

}
