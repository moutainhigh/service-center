package com.shengsu.helper.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDictionary;
import com.shengsu.helper.mapper.SystemDictionaryMapper;
import com.shengsu.helper.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("systemDictionaryService")
public class SystemDictionaryServiceImpl extends BaseServiceImpl<SystemDictionary, String> implements SystemDictionaryService {
    @Autowired
    SystemDictionaryMapper SystemDictionaryMapper;

    @Override
    public BaseMapper<SystemDictionary, String> getBaseMapper() {
        return SystemDictionaryMapper;
    }

    @Override
    public List<SystemDictionary> getManyByDictCodes(List<String> dictCodes) {
        return SystemDictionaryMapper.getManyByDictCodes(dictCodes);
    }

    @Override
    public int dictExistCheck(SystemDictionary systemDictionary) {
        return SystemDictionaryMapper.dictExistCheck(systemDictionary);
    }

    @Override
    public void create(SystemDictionary systemDictionary) {
        systemDictionary.setDictId(UUID.randomUUID().toString());
        save(systemDictionary);
    }

    @Override
    public int dictExistCheckExceptSelf(SystemDictionary systemDictionary) {
        return SystemDictionaryMapper.dictExistCheckExceptSelf(systemDictionary);
    }

    /*
     * 获取字典列表
     */
    @Override
    public List<SystemDictionary> listDicts(List<SystemDictionary> Dictionaries) {
        if (Dictionaries.isEmpty())
            return null;
        List<String> dictCodes = new ArrayList<>();
        for (SystemDictionary systemDictionary : Dictionaries) {
            dictCodes.add(systemDictionary.getDictCode());
        }
        return SystemDictionaryMapper.getMany(dictCodes);
    }

    /**
     * 包装字典
     *
     * @param Dicts
     * @param DictList
     * @return
     */
    @Override
    public Map<String, List<SystemDictionary>> makeSystemDict(List<SystemDictionary> dicts, List<SystemDictionary> dictList) {
        Map<String, List<SystemDictionary>> listMap = new HashMap<String, List<SystemDictionary>>();
        for (SystemDictionary systemDict : dicts) {
            List<SystemDictionary> systemDictsList = new ArrayList<>();
            for (SystemDictionary dict : dictList) {
                if (systemDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(systemDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }
}
