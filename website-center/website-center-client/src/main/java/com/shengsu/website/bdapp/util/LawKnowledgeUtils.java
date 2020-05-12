package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.entity.LawKnowledgeCategory;
import com.shengsu.website.bdapp.po.*;
import com.shengsu.website.bdapp.vo.LawKnowledgeCreateVo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListByPageVo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;
import com.shengsu.website.bdapp.vo.LawKnowledgeUpdateVo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 11:03
 **/
public class LawKnowledgeUtils {
    public static LawKnowledge toLawKnowledge(LawKnowledgeListPageVo lawKnowledgeListPageVo) {
        if (lawKnowledgeListPageVo != null) {
            LawKnowledge lawKnowledge = new LawKnowledge();
            lawKnowledge.setFirstCategoryId(lawKnowledgeListPageVo.getFirstCategoryId());
            lawKnowledge.setSecondCategoryId(lawKnowledgeListPageVo.getSecondCategoryId());
            lawKnowledge.setThirdCategoryId(lawKnowledgeListPageVo.getThirdCategoryId());
            lawKnowledge.setPage(lawKnowledgeListPageVo.getPage());
            lawKnowledge.setPageSize(lawKnowledgeListPageVo.getPageSize());
            return lawKnowledge;
        }
        return null;
    }

    public static LawKnowledge toLawKnowledge(LawKnowledge lawKnowledge) {
        if (lawKnowledge != null) {
            LawKnowledge result = new LawKnowledge();
            result.setFirstCategoryId(StringUtils.isBlank(lawKnowledge.getFirstCategoryId())?"":lawKnowledge.getFirstCategoryId());
            result.setSecondCategoryId(StringUtils.isBlank(lawKnowledge.getSecondCategoryId())?"":lawKnowledge.getSecondCategoryId());
            result.setThirdCategoryId(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())?"":lawKnowledge.getThirdCategoryId());
            result.setDateTime(lawKnowledge.getDateTime());
            return result;
        }
        return null;
    }
    public static LawKnowledge toLawKnowledge(String oldFirstCategoryId, String secondCategoryId) {
        LawKnowledge lawKnowledge = new LawKnowledge();
        lawKnowledge.setFirstCategoryId(oldFirstCategoryId);
        lawKnowledge.setSecondCategoryId(secondCategoryId);
        return lawKnowledge;
    }
    public static LawKnowledge toLawKnowledge(String oldFirstCategoryId, String oldSecondCategoryId,String thirdCategoryId) {
        LawKnowledge lawKnowledge = new LawKnowledge();
        lawKnowledge.setFirstCategoryId(oldFirstCategoryId);
        lawKnowledge.setSecondCategoryId(oldSecondCategoryId);
        lawKnowledge.setThirdCategoryId(thirdCategoryId);
        return lawKnowledge;
    }
    public static List<LawKnowledgeListPagePo> toLawKnowledgeListPagePos(List<LawKnowledge> lawKnowledges) {
        if (lawKnowledges != null && !lawKnowledges.isEmpty()) {
            List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = new ArrayList<>();
            for (LawKnowledge lawKnowledge : lawKnowledges){
                lawKnowledgeListPagePos.add(toLawKnowledgeListPagePo(lawKnowledge));
            }
            return lawKnowledgeListPagePos;
        }
        return null;
    }

    private static LawKnowledgeListPagePo toLawKnowledgeListPagePo(LawKnowledge lawKnowledge) {
        if (lawKnowledge != null) {
            LawKnowledgeListPagePo lawKnowledgeListPagePo = new LawKnowledgeListPagePo();
            lawKnowledgeListPagePo.setKnowledgeId(lawKnowledge.getKnowledgeId());
            lawKnowledgeListPagePo.setTitle(lawKnowledge.getTitle());
            return lawKnowledgeListPagePo;
        }
        return null;
    }

    public static LawKnowledgeCurrentPo toLawKnowledgeCurrentPo(LawKnowledge lawKnowledge) {
        if (lawKnowledge != null) {
            LawKnowledgeCurrentPo lawKnowledgeCurrentPo = new LawKnowledgeCurrentPo();
            lawKnowledgeCurrentPo.setKnowledgeId(lawKnowledge.getKnowledgeId());
            lawKnowledgeCurrentPo.setFirstCategoryId(lawKnowledge.getFirstCategoryId());
            lawKnowledgeCurrentPo.setSecondCategoryId(lawKnowledge.getSecondCategoryId());
            lawKnowledgeCurrentPo.setThirdCategoryId(lawKnowledge.getThirdCategoryId());
            lawKnowledgeCurrentPo.setTitle(lawKnowledge.getTitle());
            lawKnowledgeCurrentPo.setContent(lawKnowledge.getContent());
            lawKnowledgeCurrentPo.setDateTime(lawKnowledge.getDateTime());
            lawKnowledgeCurrentPo.setSource(lawKnowledge.getSource());
            lawKnowledgeCurrentPo.setPv(lawKnowledge.getPv());
            lawKnowledgeCurrentPo.setPictureOssId(lawKnowledge.getPictureOssId());
            return lawKnowledgeCurrentPo;
        }
        return null;
    }

    public static LawKnowledgePreviousPo toLawKnowledgePreviousPo(LawKnowledge previousLawKnowledge) {
        if (previousLawKnowledge != null) {
            LawKnowledgePreviousPo lawKnowledgePreviousPo = new LawKnowledgePreviousPo();
            lawKnowledgePreviousPo.setKnowledgeId(previousLawKnowledge.getKnowledgeId());
            lawKnowledgePreviousPo.setTitle(previousLawKnowledge.getTitle());
            return lawKnowledgePreviousPo;
        }
        return null;
    }

    public static LawKnowledgeNextPo toLawKnowledgeNextPo(LawKnowledge nextLawKnowledge) {
        if (nextLawKnowledge != null) {
            LawKnowledgeNextPo lawKnowledgeNextPo = new LawKnowledgeNextPo();
            lawKnowledgeNextPo.setKnowledgeId(nextLawKnowledge.getKnowledgeId());
            lawKnowledgeNextPo.setTitle(nextLawKnowledge.getTitle());
            return lawKnowledgeNextPo;
        }
        return null;
    }

    public static List<LawKnowledgeSimplePo> toLawKnowledgeSimplePos(List<LawKnowledge> lawKnowledges,Map<String,LawKnowledgeCategory> lawKnowledgeCategoryMap, List<Integer> itemList) {
        if (lawKnowledges != null && !lawKnowledges.isEmpty()) {
            List<LawKnowledgeSimplePo> lawKnowledgeSimplePos = new ArrayList<>();
            for (int i=0; i<lawKnowledges.size(); i++){
                LawKnowledge lawKnowledge = lawKnowledges.get(i);
                String item = itemList.get(i).toString();
                lawKnowledgeSimplePos.add(toLawKnowledgeSimplePo(lawKnowledge,lawKnowledgeCategoryMap,item));
            }
            return lawKnowledgeSimplePos;
        }
        return null;
    }

    private static LawKnowledgeSimplePo toLawKnowledgeSimplePo(LawKnowledge lawKnowledge,Map<String,LawKnowledgeCategory> lawKnowledgeCategoryMap,String item) {
        if (lawKnowledge != null) {
            LawKnowledgeSimplePo lawKnowledgeSimplePo = new LawKnowledgeSimplePo();
            lawKnowledgeSimplePo.setKnowledgeId(lawKnowledge.getKnowledgeId());
            lawKnowledgeSimplePo.setTitle(lawKnowledge.getTitle());
            if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())){
                lawKnowledgeSimplePo.setCategoryName(lawKnowledgeCategoryMap.get(lawKnowledge.getThirdCategoryId()).getCategoryName());
            }else if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())){
                lawKnowledgeSimplePo.setCategoryName(lawKnowledgeCategoryMap.get(lawKnowledge.getSecondCategoryId()).getCategoryName());
            }else if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isBlank(lawKnowledge.getSecondCategoryId())&&StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())){
                lawKnowledgeSimplePo.setCategoryName(lawKnowledgeCategoryMap.get(lawKnowledge.getFirstCategoryId()).getCategoryName());
            }
            lawKnowledgeSimplePo.setDateTime(lawKnowledge.getDateTime());
            lawKnowledgeSimplePo.setPictureOssUrl(item);
            return lawKnowledgeSimplePo;
        }
        return null;
    }
    public static LawKnowledge toLawKnowledge(LawKnowledgeCreateVo lawKnowledgeCreateVo) {
        if (lawKnowledgeCreateVo != null) {
            LawKnowledge lawKnowledge = new LawKnowledge();
            lawKnowledge.setFirstCategoryId(lawKnowledgeCreateVo.getFirstCategoryId());
            lawKnowledge.setSecondCategoryId(lawKnowledgeCreateVo.getSecondCategoryId());
            lawKnowledge.setThirdCategoryId(lawKnowledgeCreateVo.getThirdCategoryId());
            lawKnowledge.setTitle(lawKnowledgeCreateVo.getTitle());
            lawKnowledge.setContent(lawKnowledgeCreateVo.getContent());
            lawKnowledge.setDateTime(lawKnowledgeCreateVo.getDateTime());
            lawKnowledge.setSource(lawKnowledgeCreateVo.getSource());
            return lawKnowledge;
        }
        return null;
    }
    public static LawKnowledge toLawKnowledge(LawKnowledgeUpdateVo lawKnowledgeUpdateVo) {
        if (lawKnowledgeUpdateVo != null) {
            LawKnowledge lawKnowledge = new LawKnowledge();
            lawKnowledge.setKnowledgeId(lawKnowledgeUpdateVo.getKnowledgeId());
            lawKnowledge.setFirstCategoryId(lawKnowledgeUpdateVo.getFirstCategoryId());
            lawKnowledge.setSecondCategoryId(lawKnowledgeUpdateVo.getSecondCategoryId());
            lawKnowledge.setThirdCategoryId(lawKnowledgeUpdateVo.getThirdCategoryId());
            lawKnowledge.setTitle(lawKnowledgeUpdateVo.getTitle());
            lawKnowledge.setContent(lawKnowledgeUpdateVo.getContent());
            lawKnowledge.setDateTime(lawKnowledgeUpdateVo.getDateTime());
            lawKnowledge.setSource(lawKnowledgeUpdateVo.getSource());
            return lawKnowledge;
        }
        return null;
    }

    public static LawKnowledgeQueryPo toLawKnowledgeQueryPo(LawKnowledge lawKnowledge) {
        if (lawKnowledge != null) {
            LawKnowledgeQueryPo lawKnowledgeQueryPo = new LawKnowledgeQueryPo();
            lawKnowledgeQueryPo.setFirstCategoryId(lawKnowledge.getFirstCategoryId());
            lawKnowledgeQueryPo.setSecondCategoryId(lawKnowledge.getSecondCategoryId());
            lawKnowledgeQueryPo.setThirdCategoryId(lawKnowledge.getThirdCategoryId());
            lawKnowledgeQueryPo.setTitle(lawKnowledge.getTitle());
            lawKnowledgeQueryPo.setContent(lawKnowledge.getContent());
            lawKnowledgeQueryPo.setDateTime(lawKnowledge.getDateTime());
            lawKnowledgeQueryPo.setSource(lawKnowledge.getSource());
            return lawKnowledgeQueryPo;
        }
        return null;
    }
    public static LawKnowledge toLawKnowledge(LawKnowledgeListByPageVo lawKnowledgeListByPageVo) {
        if (lawKnowledgeListByPageVo != null) {
            LawKnowledge lawKnowledge = new LawKnowledge();
            lawKnowledge.setFirstCategoryId(lawKnowledgeListByPageVo.getFirstCategoryId());
            lawKnowledge.setSecondCategoryId(lawKnowledgeListByPageVo.getSecondCategoryId());
            lawKnowledge.setThirdCategoryId(lawKnowledgeListByPageVo.getThirdCategoryId());
            lawKnowledge.setTitle(lawKnowledgeListByPageVo.getTitle());
            lawKnowledge.setPage(lawKnowledgeListByPageVo.getPage());
            lawKnowledge.setPageSize(lawKnowledgeListByPageVo.getPageSize());
            return lawKnowledge;
        }
        return null;
    }

    public static List<LawKnowledgePagePo> toLawKnowledgePagePos(List<LawKnowledge> lawKnowledges) {
        if (lawKnowledges != null && !lawKnowledges.isEmpty()) {
            List<LawKnowledgePagePo> lawKnowledgePagePos = new ArrayList<>();
            for (LawKnowledge lawKnowledge : lawKnowledges){
                lawKnowledgePagePos.add(toLawKnowledgePagePo(lawKnowledge));
            }
            return lawKnowledgePagePos;
        }
        return null;
    }
    private static LawKnowledgePagePo toLawKnowledgePagePo(LawKnowledge lawKnowledge) {
        if (lawKnowledge != null) {
            LawKnowledgePagePo lawKnowledgePagePo = new LawKnowledgePagePo();
            lawKnowledgePagePo.setKnowledgeId(lawKnowledge.getKnowledgeId());
            lawKnowledgePagePo.setFirstCategoryId(lawKnowledge.getFirstCategoryId());
            lawKnowledgePagePo.setSecondCategoryId(lawKnowledge.getSecondCategoryId());
            lawKnowledgePagePo.setThirdCategoryId(lawKnowledge.getThirdCategoryId());
            lawKnowledgePagePo.setTitle(lawKnowledge.getTitle());
            lawKnowledgePagePo.setDateTime(lawKnowledge.getDateTime());
            return lawKnowledgePagePo;
        }
        return null;
    }
}
