package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.LawKnowledgeCategory;
import com.shengsu.website.bdapp.po.LawKnowledgeCategoryListPo;

import java.util.ArrayList;
import java.util.List;

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
}
