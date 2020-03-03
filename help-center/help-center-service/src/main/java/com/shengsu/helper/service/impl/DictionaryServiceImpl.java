package com.shengsu.helper.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.Dictionary;
import com.shengsu.helper.mapper.DictionaryMapper;
import com.shengsu.helper.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, String> implements DictionaryService {
    @Autowired
    DictionaryMapper DictionaryMapper;

    @Override
    public BaseMapper<Dictionary, String> getBaseMapper() {
        return DictionaryMapper;
    }

    @Override
    public List<Dictionary> getManyByDictCodes(List<String> dictCodes) {
        return DictionaryMapper.getManyByDictCodes(dictCodes);
    }

    @Override
    public int dictExistCheck(Dictionary dictionary) {
        return DictionaryMapper.dictExistCheck(dictionary);
    }

    @Override
    public void create(Dictionary dictionary) {
        dictionary.setDictId(UUID.randomUUID().toString());
        save(dictionary);
    }

    @Override
    public int dictExistCheckExceptSelf(Dictionary dictionary) {
        return DictionaryMapper.dictExistCheckExceptSelf(dictionary);
    }

    /*
     * 获取字典列表
     */
    @Override
    public List<Dictionary> listDicts(List<Dictionary> Dictionaries) {
        if (Dictionaries.isEmpty())
            return null;
        List<String> dictCodes = new ArrayList<>();
        for (Dictionary dictionary : Dictionaries) {
            dictCodes.add(dictionary.getDictCode());
        }
        return DictionaryMapper.getMany(dictCodes);
    }

    /**
     * 包装字典
     *
     * @param Dicts
     * @param DictList
     * @return
     */
    @Override
    public Map<String, List<Dictionary>> makeSystemDict(List<Dictionary> dicts, List<Dictionary> dictList) {
        Map<String, List<Dictionary>> listMap = new HashMap<String, List<Dictionary>>();
        for (Dictionary systemDict : dicts) {
            List<Dictionary> systemDictsList = new ArrayList<>();
            for (Dictionary dict : dictList) {
                if (systemDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(systemDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }
}
