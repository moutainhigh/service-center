package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.po.ClueLibPo;
import com.shengsu.any.clue.po.ClueListPo;
import com.shengsu.any.clue.po.MyCluePo;
import com.shengsu.any.clue.vo.ClueEditVo;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.any.clue.vo.ClueVo;
import com.shengsu.helper.entity.SystemDict;

import java.util.*;

public class ClueUtils {
    public static void setClueTypes(List<Clue> clues,Map<String, SystemDict> systemDictMap){
        for(Clue clue : clues){
            SystemDict systemDict = systemDictMap.get(clue.getClueType());
            clue.setClueType(systemDict==null?"":systemDict.getDisplayName());
        }
    }
    public static Clue toClue(ClueShelfVo clueShelfVo){
        Clue clue = new Clue();
        clue.setClueId(clueShelfVo.getClueId());
        return clue;
    }
    public static Clue toClue(ClueVo clueVo){
        Clue clue = new Clue();
        clue.setClueId(UUID.randomUUID().toString());
        clue.setAppellation(clueVo.getAppellation());
        clue.setProvinceCode(clueVo.getProvinceCode());
        clue.setClueType(clueVo.getClueType());
        clue.setCityCode(clueVo.getCityCode());
        clue.setDistrictCode(clueVo.getDistrictCode());
        clue.setCluePrice(clueVo.getCluePrice());
        clue.setCustomerDemands(clueVo.getCustomerDemands());
        clue.setTel(clueVo.getTel());
        return clue;
    }
    public static Clue toClue(ClueEditVo clueVo){
        Clue clue = new Clue();
        clue.setClueId(clueVo.getClueId());
        clue.setAppellation(clueVo.getAppellation());
        clue.setProvinceCode(clueVo.getProvinceCode());
        clue.setClueType(clueVo.getClueType());
        clue.setCityCode(clueVo.getCityCode());
        clue.setDistrictCode(clueVo.getDistrictCode());
        clue.setCluePrice(clueVo.getCluePrice());
        clue.setCustomerDemands(clueVo.getCustomerDemands());
        clue.setTel(clueVo.getTel());
        return clue;
    }

    public static List<ClueLibPo> toClue(List<Clue> clues, Map<String,SystemDict> systemDictMap, List<Pns> pnss, Integer expireTimeSecond) {
        List<ClueLibPo> list = new ArrayList<>();
        for (Clue clue : clues) {
            ClueLibPo clueLibPo = new ClueLibPo();
            clueLibPo.setClueState(clue.getClueState());
            clueLibPo.setAppellation(clue.getAppellation());
            clueLibPo.setCityCode(clue.getCityCode());
            clueLibPo.setClueCode(clue.getClueCode());
            clueLibPo.setProvinceCode(clue.getProvinceCode());
            clueLibPo.setCluePrice(clue.getCluePrice());
            SystemDict systemDict = systemDictMap.get(clue.getClueType());
            clueLibPo.setClueType(systemDict==null?"":systemDict.getDisplayName());
            clueLibPo.setDistrictCode(clue.getDistrictCode());
            clueLibPo.setCustomerDemands(clue.getCustomerDemands());
            clueLibPo.setTel(clue.getTel());
            clueLibPo.setClueCode(clue.getClueCode());
            clueLibPo.setOffshelfTime(clue.getOffshelfTime());
            clueLibPo.setOnshelfTime(clue.getOnshelfTime());
            clueLibPo.setClueId(clue.getClueId());
            clueLibPo.setCreateTime(clue.getCreateTime());
            list.add(clueLibPo);
        }
        for (ClueLibPo clueLibPo : list) {
            for (Pns pns : pnss) {
                if (clueLibPo.getClueId().equals(pns.getClueId())) {
                    Date now = new Date();
                    Date date = pns.getBindTime();
                    clueLibPo.setTelX(pns.getTelx());
                    clueLibPo.setBindTime(pns.getBindTime());
                    clueLibPo.setExpiration(pns.getExpiration());
                    if (now.getTime() - date.getTime() > expireTimeSecond * 1000) {
                        clueLibPo.setPnsStatus("Expired");
                    } else {
                        clueLibPo.setPnsStatus("Valid");
                    }
                }
            }
        }
        return list;
    }

    public static List<ClueListPo> toClueClientPo(List<Clue> clues,Map<String,SystemDict> systemDictMap) {
        List<ClueListPo> list = new ArrayList<>();
        for (Clue clue : clues) {
            ClueListPo clueListPo = new ClueListPo();
            clueListPo.setClueState(clue.getClueState());
            clueListPo.setAppellation(clue.getAppellation());
            clueListPo.setCityCode(clue.getCityCode());
            clueListPo.setClueCode(clue.getClueCode());
            clueListPo.setProvinceCode(clue.getProvinceCode());
            clueListPo.setCluePrice(clue.getCluePrice());
            SystemDict systemDict = systemDictMap.get(clue.getClueType());
            clueListPo.setClueType(systemDict==null?"":systemDict.getDisplayName());
            clueListPo.setDistrictCode(clue.getDistrictCode());
            clueListPo.setCustomerDemands(clue.getCustomerDemands());
            clueListPo.setClueCode(clue.getClueCode());
            clueListPo.setOnshelfTime(clue.getOnshelfTime());
            clueListPo.setClueId(clue.getClueId());
            list.add(clueListPo);
        }
        return list;
    }

    public static List<MyCluePo> toClueWebPagePo(List<Clue> clues, List<CluePersonal> cluePersonals, List<Pns> pnss,Map<String,SystemDict> systemDictMap, Integer expireTimeSecond) {
        List<MyCluePo> list = new ArrayList<>();
        for (CluePersonal cluePersonal : cluePersonals) {
            for (Clue clue : clues) {
                for (Pns pns : pnss) {
                    if (clue.getClueId().equals(cluePersonal.getClueId()) && clue.getClueId().equals(pns.getClueId())) {
                        Date now = new Date();
                        Date date = pns.getBindTime();
                        MyCluePo myCluePo = new MyCluePo();
                        myCluePo.setClueState(clue.getClueState());
                        myCluePo.setAppellation(clue.getAppellation());
                        myCluePo.setCityCode(clue.getCityCode());
                        myCluePo.setClueCode(clue.getClueCode());
                        myCluePo.setProvinceCode(clue.getProvinceCode());
                        myCluePo.setCluePrice(clue.getCluePrice());
                        SystemDict systemDict = systemDictMap.get(clue.getClueType());
                        myCluePo.setClueType(systemDict==null?"":systemDict.getDisplayName());
                        myCluePo.setDistrictCode(clue.getDistrictCode());
                        myCluePo.setCustomerDemands(clue.getCustomerDemands());
                        myCluePo.setTel(clue.getTel());
                        myCluePo.setClueCode(clue.getClueCode());
                        myCluePo.setClueId(clue.getClueId());
                        myCluePo.setBuyTime(cluePersonal.getCreateTime());
                        myCluePo.setTelx(pns.getTelx());
                        if (now.getTime() - date.getTime() > expireTimeSecond * 1000) {
                            myCluePo.setPnsStatus("Expired");
                        } else {
                            myCluePo.setPnsStatus("Valid");
                        }
                        list.add(myCluePo);
                    }
                }
            }
        }
        return list;
    }

    public static void checkPnsStatus() {

    }

    public static List<String> toClueId(List<Clue> clues){
        List<String> list = new ArrayList<> ();
        for(Clue clue : clues){
            String clueId = clue.getClueId();
            list.add(clueId);
        }
        return list;
    }
    public static Map<String, Clue> toClueMap(List<Clue> clues) {
        if (clues != null) {
            Map<String, Clue> clueMap = new HashMap<>();
            for (Clue clue : clues) {
                clueMap.put(clue.getClueId(), clue);
            }
            return clueMap;
        }
        return null;
    }
}
