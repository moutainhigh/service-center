package com.shengsu.helper.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.mapper.SystemDictMapper;
import com.shengsu.helper.service.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("systemDictService")
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDict, String> implements SystemDictService {
    @Autowired
    SystemDictMapper SystemDictMapper;

    @Override
    public BaseMapper<SystemDict, String> getBaseMapper() {
        return SystemDictMapper;
    }

    @Override
    public List<SystemDict> getManyByDictCodes(List<String> dictCodes) {
        return SystemDictMapper.getManyByDictCodes(dictCodes);
    }

    @Override
    public int dictExistCheck(SystemDict systemDict) {
        return SystemDictMapper.dictExistCheck(systemDict);
    }

    @Override
    public void create(SystemDict systemDict) {
        systemDict.setDictId(UUID.randomUUID().toString());
        save(systemDict);
    }

    @Override
    public int dictExistCheckExceptSelf(SystemDict systemDict) {
        return SystemDictMapper.dictExistCheckExceptSelf(systemDict);
    }

    /*
     * 获取字典列表
     */
    @Override
    public List<SystemDict> listDicts(List<SystemDict> Dictionaries) {
        if (Dictionaries.isEmpty())
            return null;
        List<String> dictCodes = new ArrayList<>();
        for (SystemDict systemDict : Dictionaries) {
            dictCodes.add(systemDict.getDictCode());
        }
        return SystemDictMapper.getMany(dictCodes);
    }

    /**
     * 包装字典
     *
     * @param Dicts
     * @param DictList
     * @return
     */
    @Override
    public Map<String, List<SystemDict>> makeSystemDict(List<SystemDict> dicts, List<SystemDict> dictList) {
        Map<String, List<SystemDict>> listMap = new HashMap<String, List<SystemDict>>();
        for (SystemDict systemDict : dicts) {
            List<SystemDict> systemDictsList = new ArrayList<>();
            for (SystemDict dict : dictList) {
                if (systemDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(systemDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }
}
