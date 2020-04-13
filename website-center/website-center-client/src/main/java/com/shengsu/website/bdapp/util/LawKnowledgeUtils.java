package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.po.LawKnowledgeCurrentPo;
import com.shengsu.website.bdapp.po.LawKnowledgeListPagePo;
import com.shengsu.website.bdapp.po.LawKnowledgeNextPo;
import com.shengsu.website.bdapp.po.LawKnowledgePreviousPo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;

import java.util.ArrayList;
import java.util.Date;
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

    public static LawKnowledge toLawKnowledge(Date dateTime, String thirdCategoryId) {
        if (dateTime != null) {
            LawKnowledge lawKnowledge = new LawKnowledge();
            lawKnowledge.setDateTime(dateTime);
            lawKnowledge.setThirdCategoryId(thirdCategoryId);
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
}
