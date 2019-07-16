package com.shengsu.system.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.system.entity.SystemDictionary;
import com.shengsu.system.mapper.SystemDictionaryMapper;
import com.shengsu.system.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("systemDictionaryService")
public class SystemDictionaryServiceImpl extends BaseServiceImpl<SystemDictionary,String> implements SystemDictionaryService {
    @Autowired
    SystemDictionaryMapper systemDictionaryMapper;
    @Override
    public BaseMapper<SystemDictionary, String> getBaseMapper() {
        return systemDictionaryMapper;
    }

    @Override
    public List<SystemDictionary> getManyByDictCodes(List<String> dictCodes) {
        return systemDictionaryMapper.getManyByDictCodes(dictCodes);
    }
    @Override
    public int dictExistCheck(SystemDictionary systemDictionary) {
        return systemDictionaryMapper.dictExistCheck(systemDictionary);
    }
    @Override
    public void create(SystemDictionary systemDictionary) {
        systemDictionary.setDictId(UUID.randomUUID().toString());
        save(systemDictionary);
    }
    @Override
    public int dictExistCheckExceptSelf(SystemDictionary systemDictionary) {
        return systemDictionaryMapper.dictExistCheckExceptSelf(systemDictionary);
    }
    /*
     * 获取字典列表
     */
    @Override
    public List<SystemDictionary> listDicts(List<SystemDictionary> systemDictionaries) {
        if (systemDictionaries.isEmpty())
            return null;
        List<String>dictCodes  = new ArrayList<>();
        for (SystemDictionary systemDictionary : systemDictionaries) {
            dictCodes.add(systemDictionary.getDictCode());
        }
        return systemDictionaryMapper.getMany(dictCodes);
    }
    /**
     * 包装字典
     * @param systemDicts
     * @param systemDictList
     * @return
     */
    @Override
    public Map<String, List<SystemDictionary>> makeSystemDict(List<SystemDictionary> systemDicts, List<SystemDictionary> systemDictList) {
        Map<String, List<SystemDictionary>> listMap = new HashMap<String, List<SystemDictionary>>();
        for (SystemDictionary systemDict:systemDicts) {
            List<SystemDictionary> systemDictsList =new ArrayList<>();
            for (SystemDictionary dict:systemDictList) {
                if (systemDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(systemDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }
}
