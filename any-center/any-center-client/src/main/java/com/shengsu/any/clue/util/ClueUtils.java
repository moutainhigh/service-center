package com.shengsu.any.clue.util;

import com.shengsu.any.clue.Po.CluePo;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.vo.ClueEditVo;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.any.clue.vo.ClueVo;
import com.shengsu.helper.service.CodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public static List<CluePo> toClue(List<Clue> clues){
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
        list.add(cluePo);}
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
}
