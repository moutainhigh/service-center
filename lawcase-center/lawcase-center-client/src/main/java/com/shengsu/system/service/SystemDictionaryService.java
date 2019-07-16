package com.shengsu.system.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.system.entity.SystemDictionary;

import java.util.List;
import java.util.Map;


public interface SystemDictionaryService extends BaseService<SystemDictionary,String> {
    List<SystemDictionary> getManyByDictCodes(List<String> dictCodes);
    int dictExistCheck(SystemDictionary systemDictionary);
    void create(SystemDictionary systemDictionary);
    int dictExistCheckExceptSelf(SystemDictionary systemDictionary);
    List<SystemDictionary> listDicts(List<SystemDictionary> systemDictionaries);
    Map<String, List<SystemDictionary>> makeSystemDict(List<SystemDictionary> systemDicts, List<SystemDictionary> systemDictList);

}
