package com.shengsu.system.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.system.entity.LawcaseDictionary;

import java.util.List;
import java.util.Map;


public interface LawcaseDictionaryService extends BaseService<LawcaseDictionary,String> {
    List<LawcaseDictionary> getManyByDictCodes(List<String> dictCodes);
    int dictExistCheck(LawcaseDictionary lawcaseDictionary);
    void create(LawcaseDictionary lawcaseDictionary);
    int dictExistCheckExceptSelf(LawcaseDictionary lawcaseDictionary);
    List<LawcaseDictionary> listDicts(List<LawcaseDictionary> systemDictionaries);
    Map<String, List<LawcaseDictionary>> makeSystemDict(List<LawcaseDictionary> systemDicts, List<LawcaseDictionary> systemDictList);

}
