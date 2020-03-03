package com.shengsu.helper.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.helper.entity.Dictionary;

import java.util.List;
import java.util.Map;


public interface DictionaryService extends BaseService<Dictionary, String> {
    List<Dictionary> getManyByDictCodes(List<String> dictCodes);

    int dictExistCheck(Dictionary Dictionary);

    void create(Dictionary Dictionary);

    int dictExistCheckExceptSelf(Dictionary Dictionary);

    List<Dictionary> listDicts(List<Dictionary> systemDictionaries);

    Map<String, List<Dictionary>> makeSystemDict(List<Dictionary> systemDicts, List<Dictionary> systemDictList);

}
