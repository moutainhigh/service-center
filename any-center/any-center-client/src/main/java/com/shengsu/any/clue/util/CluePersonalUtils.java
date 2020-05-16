package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.po.CluePersonalPo;
import com.shengsu.any.clue.vo.CluePersonalVo;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserPo;
import com.shengsu.any.user.util.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-16 11:14
 **/
public class CluePersonalUtils {
    public static  List<CluePersonalPo> toCluePersonalPos(List<CluePersonal> personals , Map<String,User> userMap, Map<String,Clue> clueMap){
        List<CluePersonalPo> cluePersonalPos = new ArrayList<>();
        for(CluePersonal cluePersonal : personals){
            CluePersonalPo cluePersonalPo = new CluePersonalPo();
            UserPo userPo = UserUtils.toUserPo(userMap.get(cluePersonal.getUserId()));
            Clue clue = clueMap.get(cluePersonal.getClueId());
            if(userPo==null || clue==null){
                continue;
            }
            cluePersonalPo.setUserPo(userPo);
            cluePersonalPo.setClueCode(clue.getClueCode());
            cluePersonalPo.setOnshelfTime(clue.getOnshelfTime());
            cluePersonalPo.setProvinceCode(clue.getProvinceCode());
            cluePersonalPo.setCityCode(clue.getCityCode());
            cluePersonalPo.setDistrictCode(clue.getDistrictCode());
            cluePersonalPo.setClueType(clue.getClueType());
            cluePersonalPo.setBuyTime(cluePersonal.getCreateTime());
            cluePersonalPo.setCluePrice(clue.getCluePrice());
            cluePersonalPos.add(cluePersonalPo);
        }
        return cluePersonalPos;
    }
    public static CluePersonal toCluePersonal(CluePersonalVo cluePersonalVo){
        CluePersonal cluePersonal = new CluePersonal();
        cluePersonal.setCreateTime(cluePersonalVo.getCreateTime());
        return cluePersonal;
    }
}
