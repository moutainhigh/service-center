package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.LawKnowledgeCategory;
import com.shengsu.website.market.po.LawKnowledgeCategoryListPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LawKnowledgeCategoryUtils {

    public static List<LawKnowledgeCategoryListPo> toLawKnowledgeCategoryListPos(List<LawKnowledgeCategory> lawKnowledgeCategories) {
        if(lawKnowledgeCategories !=null && !lawKnowledgeCategories.isEmpty()){
            List<LawKnowledgeCategoryListPo> lawKnowledgeCategoryListPos = new ArrayList<>();
            for (LawKnowledgeCategory lawKnowledgeCategory : lawKnowledgeCategories){
                lawKnowledgeCategoryListPos.add(toLawKnowledgeCategoryPo(lawKnowledgeCategory));
            }
            return lawKnowledgeCategoryListPos;
        }
        return null;
    }

    private static LawKnowledgeCategoryListPo toLawKnowledgeCategoryPo(LawKnowledgeCategory lawKnowledgeCategory) {
        if (lawKnowledgeCategory != null) {
            LawKnowledgeCategoryListPo lawKnowledgeCategoryListPo = new LawKnowledgeCategoryListPo();
            lawKnowledgeCategoryListPo.setCategoryId(lawKnowledgeCategory.getCategoryId());
            lawKnowledgeCategoryListPo.setCategoryName(lawKnowledgeCategory.getCategoryName());
            lawKnowledgeCategoryListPo.setParentId(lawKnowledgeCategory.getParentId());
            lawKnowledgeCategoryListPo.setSortNum(lawKnowledgeCategory.getSortNum());
            return lawKnowledgeCategoryListPo;
        }
        return null;
    }

    public static Map<String,LawKnowledgeCategory> toLawKnowledgeCategoryMap(List<LawKnowledgeCategory> lawKnowledgeCategories) {
        Map<String,LawKnowledgeCategory> result = new HashMap();
        if(lawKnowledgeCategories !=null && !lawKnowledgeCategories.isEmpty()){
            for(LawKnowledgeCategory lawKnowledgeCategory:lawKnowledgeCategories){
                result.put(lawKnowledgeCategory.getCategoryId(),lawKnowledgeCategory);
            }
        }
        return result;
    }
}
