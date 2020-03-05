package com.shengsu.util;

import com.shengsu.helper.entity.SystemDict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-03-05 19:05
 **/
public class SystemDictUtil {
    public static List<String> toDictCodes(List<SystemDict> Dictionaries) {
        if (Dictionaries.isEmpty())
            return null;
        List<String> dictCodes = new ArrayList<>();
        for (SystemDict systemDict : Dictionaries) {
            dictCodes.add(systemDict.getDictCode());
        }
        return dictCodes;
    }

    public static Map<String, Object> toSystemMap(String dictCode, String displayValue) {
        Map<String, Object> systemMap = new HashMap<>();
        systemMap.put("dictCode", dictCode);
        systemMap.put("displayValue", displayValue);
        return systemMap;
    }

    public static Map<String, Object> toSystemDictListMap(List<SystemDict> systemDictList) {
        Map<String, Object> map = new HashMap<String, Object>();
        //遍历包装
        Map<String, List<SystemDict>> systemDictMap = makeSystemDict(systemDictList, systemDictList);
        map.put("root", systemDictMap);
        return map;
    }

    public static Map<String, List<SystemDict>> makeSystemDict(List<SystemDict> dicts, List<SystemDict> dictList) {
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

    public static Map<String, Object> toDisPlayNameMap(String dictCode, List<String> displayValues) {
        Map<String, Object> disPlayName = new HashMap<>();
        disPlayName.put("dictCode", dictCode);
        disPlayName.put("displayValue", displayValues);
        return disPlayName;
    }
}
