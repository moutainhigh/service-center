package com.shengsu.system.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.system.entity.LawcaseDictionary;
import com.shengsu.system.mapper.LawcaseDictionaryMapper;
import com.shengsu.system.service.LawcaseDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("lawcaseDictionaryService")
public class LawcaseDictionaryServiceImpl extends BaseServiceImpl<LawcaseDictionary,String> implements LawcaseDictionaryService {
    @Autowired
    LawcaseDictionaryMapper lawcaseDictionaryMapper;
    @Override
    public BaseMapper<LawcaseDictionary, String> getBaseMapper() {
        return lawcaseDictionaryMapper;
    }

    @Override
    public List<LawcaseDictionary> getManyByDictCodes(List<String> dictCodes) {
        return lawcaseDictionaryMapper.getManyByDictCodes(dictCodes);
    }
    @Override
    public int dictExistCheck(LawcaseDictionary lawcaseDictionary) {
        return lawcaseDictionaryMapper.dictExistCheck(lawcaseDictionary);
    }
    @Override
    public void create(LawcaseDictionary lawcaseDictionary) {
        lawcaseDictionary.setDictId(UUID.randomUUID().toString());
        save(lawcaseDictionary);
    }
    @Override
    public int dictExistCheckExceptSelf(LawcaseDictionary lawcaseDictionary) {
        return lawcaseDictionaryMapper.dictExistCheckExceptSelf(lawcaseDictionary);
    }
    /*
     * 获取字典列表
     */
    @Override
    public List<LawcaseDictionary> listDicts(List<LawcaseDictionary> lawcaseDictionaries) {
        if (lawcaseDictionaries.isEmpty())
            return null;
        List<String>dictCodes  = new ArrayList<>();
        for (LawcaseDictionary lawcaseDictionary : lawcaseDictionaries) {
            dictCodes.add(lawcaseDictionary.getDictCode());
        }
        return lawcaseDictionaryMapper.getMany(dictCodes);
    }
    /**
     * 包装字典
     * @param lawcaseDicts
     * @param lawcaseDictList
     * @return
     */
    @Override
    public Map<String, List<LawcaseDictionary>> makeSystemDict(List<LawcaseDictionary> lawcaseDicts, List<LawcaseDictionary> lawcaseDictList) {
        Map<String, List<LawcaseDictionary>> listMap = new HashMap<String, List<LawcaseDictionary>>();
        for (LawcaseDictionary lawcaseDict:lawcaseDicts) {
            List<LawcaseDictionary> systemDictsList =new ArrayList<>();
            for (LawcaseDictionary dict:lawcaseDictList) {
                if (lawcaseDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(lawcaseDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }
}
