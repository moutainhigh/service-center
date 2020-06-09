package com.shengsu.website.market.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 15:37
 **/
@Data
public class LawKnowledgeDetailsPo implements Serializable {
    private LawKnowledgeCurrentPo lawKnowledgeCurrentPo;

    private LawKnowledgePreviousPo lawKnowledgePreviousPo;

    private LawKnowledgeNextPo lawKnowledgeNextPo;
}
