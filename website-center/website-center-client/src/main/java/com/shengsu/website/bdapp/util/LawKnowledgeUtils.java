package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.po.LawKnowledgeListPagePo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;

import java.util.ArrayList;
import java.util.List;

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
}
