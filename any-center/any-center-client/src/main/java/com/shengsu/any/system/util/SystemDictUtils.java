package com.shengsu.any.system.util;


import com.shengsu.any.clue.entity.Clue;
import com.shengsu.helper.entity.SystemDict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemDictUtils {

    public static Map<String, SystemDict> toSystemDictMap(List<SystemDict> systemDicts){
        if (systemDicts != null) {
            Map<String, SystemDict> systemDictMap = new HashMap<>();
            for (SystemDict systemDict : systemDicts) {
                systemDictMap.put(systemDict.getDisplayValue(), systemDict);
            }
            return systemDictMap;
        }
        return null;

    }

    public static void toSystemDicts(List<SystemDict> systemDicts, List<Clue> clues) {
        for (SystemDict systemDict : systemDicts) {
            for (Clue clue : clues) {
                if (systemDict.getDisplayValue().equals(clue.getClueType())) {
                    clue.setClueType(systemDict.getDisplayName());
                }
            }
        }
    }
}
