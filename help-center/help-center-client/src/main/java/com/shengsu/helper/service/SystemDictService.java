package com.shengsu.helper.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.helper.entity.SystemDict;

import java.util.List;
import java.util.Map;


public interface SystemDictService extends BaseService<SystemDict, String> {
    List<SystemDict> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDict SystemDict);

    void create(SystemDict SystemDict);

    int dictExistCheckExceptSelf(SystemDict SystemDict);

    List<SystemDict> listDicts(List<SystemDict> systemDictionaries);

    Map<String, List<SystemDict>> makeSystemDict(List<SystemDict> systemDicts, List<SystemDict> systemDictList);

}
