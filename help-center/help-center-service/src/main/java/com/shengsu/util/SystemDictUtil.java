package com.shengsu.util;

import com.shengsu.helper.entity.SystemDict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static Map<String, Object> toParamMap(String dictCode, String displayValue) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dictCode", dictCode);
        paramMap.put("displayValue", displayValue);
        return paramMap;
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

    public static Map<String, Object> toParamMap(String dictCode, List<String> displayValues) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dictCode", dictCode);
        paramMap.put("displayValue", displayValues);
        return paramMap;
    }

    public static Map<String, SystemDict> toSystemDictMap(List<SystemDict> systemDicts){
        if (systemDicts != null) {
            return systemDicts.stream().collect(Collectors.toMap(SystemDict::getDisplayValue,Function.identity()));
        }
        return null;

    }
}
