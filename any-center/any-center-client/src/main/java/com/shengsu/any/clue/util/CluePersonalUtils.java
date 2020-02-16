package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.vo.CluePersonVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-14 12:18
 **/
public class CluePersonalUtils {
    public static CluePersonVo toClueId(List<CluePersonal> cluePersonals){
        List<String> list = new ArrayList<>();
        List<Date> createTimeList = new ArrayList<>();
        for(CluePersonal cluePersonal : cluePersonals){
            String clueId =cluePersonal.getClueId();
            Date createTime = cluePersonal.getCreateTime();
            createTimeList.add(createTime);
            list.add(clueId);
        }
        CluePersonVo cluePersonVo = new CluePersonVo();
        cluePersonVo.setClueId(list);
        cluePersonVo.setCreateTime(createTimeList);
        return cluePersonVo;
    }
}
