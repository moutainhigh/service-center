package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.po.ClueClientPo;
import com.shengsu.any.clue.po.CluePo;
import com.shengsu.any.clue.po.ClueWebPagePo;
import com.shengsu.any.clue.vo.ClueEditVo;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.any.clue.vo.ClueVo;
import com.shengsu.helper.service.CodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ClueUtils {
    @Autowired
    private CodeGeneratorService codeGeneratorService;
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
    public static List<CluePo> toClue(List<Clue> clues,List<Pns> pnss){
        List<CluePo> list = new ArrayList<>();
        for(Clue clue : clues){
            CluePo cluePo = new CluePo();
            cluePo.setClueState(clue.getClueState());
            cluePo.setAppellation(clue.getAppellation());
            cluePo.setCityCode(clue.getCityCode());
            cluePo.setClueCode(clue.getClueCode());
            cluePo.setProvinceCode(clue.getProvinceCode());
            cluePo.setCluePrice(clue.getCluePrice());
            cluePo.setClueType(clue.getClueType());
            cluePo.setDistrictCode(clue.getDistrictCode());
            cluePo.setCustomerDemands(clue.getCustomerDemands());
            cluePo.setTel(clue.getTel());
            cluePo.setClueCode(clue.getClueCode());
            cluePo.setOffshelfTime(clue.getOffshelfTime());
            cluePo.setOnshelfTime(clue.getOnshelfTime());
            cluePo.setClueId(clue.getClueId());
            cluePo.setCreateTime(clue.getCreateTime());
            list.add(cluePo);
        }
        for (CluePo cluePo : list) {
            for (Pns pns : pnss) {
                if (cluePo.getClueId().equals(pns.getClueId())) {
                    cluePo.setTelX(pns.getTelx());
                    cluePo.setBindTime(pns.getBindTime());
                    cluePo.setExpiration(pns.getExpiration());
                }
            }
        }
        return list;
    }
    public static List<ClueClientPo> toClueClientPo(List<Clue> clues){
        List<ClueClientPo> list = new ArrayList<>();
        for(Clue clue : clues){
            ClueClientPo clueClientPo = new ClueClientPo();
            clueClientPo.setClueState(clue.getClueState());
            clueClientPo.setAppellation(clue.getAppellation());
            clueClientPo.setCityCode(clue.getCityCode());
            clueClientPo.setClueCode(clue.getClueCode());
            clueClientPo.setProvinceCode(clue.getProvinceCode());
            clueClientPo.setCluePrice(clue.getCluePrice());
            clueClientPo.setClueType(clue.getClueType());
            clueClientPo.setDistrictCode(clue.getDistrictCode());
            clueClientPo.setCustomerDemands(clue.getCustomerDemands());
            clueClientPo.setClueCode(clue.getClueCode());
            clueClientPo.setOnshelfTime(clue.getOnshelfTime());
            clueClientPo.setClueId(clue.getClueId());
            list.add(clueClientPo);
        }
        return list;
    }

    public static List<ClueWebPagePo> toClueWebPagePo(List<Clue> clues, List<CluePersonal> cluePersonals,List<Pns> pnss) {
        List<ClueWebPagePo> list = new ArrayList<>();
        for (CluePersonal cluePersonal : cluePersonals) {
            for (Clue clue : clues) {
                for(Pns pns : pnss){
                if (clue.getClueId().equals(cluePersonal.getClueId()) && clue.getClueId().equals(pns.getClueId())) {
                    ClueWebPagePo clueWebPagePo = new ClueWebPagePo();
                    clueWebPagePo.setClueState(clue.getClueState());
                    clueWebPagePo.setAppellation(clue.getAppellation());
                    clueWebPagePo.setCityCode(clue.getCityCode());
                    clueWebPagePo.setClueCode(clue.getClueCode());
                    clueWebPagePo.setProvinceCode(clue.getProvinceCode());
                    clueWebPagePo.setCluePrice(clue.getCluePrice());
                    clueWebPagePo.setClueType(clue.getClueType());
                    clueWebPagePo.setDistrictCode(clue.getDistrictCode());
                    clueWebPagePo.setCustomerDemands(clue.getCustomerDemands());
                    clueWebPagePo.setTel(clue.getTel());
                    clueWebPagePo.setClueCode(clue.getClueCode());
                    clueWebPagePo.setClueId(clue.getClueId());
                    clueWebPagePo.setBuyTime(cluePersonal.getCreateTime());
                    clueWebPagePo.setTelx(pns.getTelx());
                    list.add(clueWebPagePo);
                }
                }
            }
        }
        return list;
    }

    public static List<String> toClueType(List<Clue> clues){
        List<String> list = new ArrayList<> ();
        for(Clue clue : clues){
            String clueType = clue.getClueType();
            list.add(clueType);
        }
        return list;
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
