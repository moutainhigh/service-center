package com.shengsu.helper.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.helper.entity.SystemDictionary;

import java.util.List;
import java.util.Map;


public interface SystemDictionaryService extends BaseService<SystemDictionary, String> {
    List<SystemDictionary> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(SystemDictionary SystemDictionary);

    void create(SystemDictionary SystemDictionary);

    int dictExistCheckExceptSelf(SystemDictionary SystemDictionary);

    List<SystemDictionary> listDicts(List<SystemDictionary> systemDictionaries);

    Map<String, List<SystemDictionary>> makeSystemDict(List<SystemDictionary> systemDicts, List<SystemDictionary> systemDictList);

}
