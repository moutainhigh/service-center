package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.CluePersonal;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-14 12:18
 **/
public class CluePersonalUtils {
    public static List<String> toClueId(List<CluePersonal> cluePersonals){
        List<String> list = new ArrayList<>();
        for(CluePersonal cluePersonal : cluePersonals){
            String clueId =cluePersonal.getClueId();
            list.add(clueId);
        }
        return list;
    }
}
