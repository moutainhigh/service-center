package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LawKnowledgeMapper extends BaseMapper<LawKnowledge, String> {
    LawKnowledge selectByKnowledgeId(String knowledgeId);

    LawKnowledge selectPreviousLawKnowledge(LawKnowledge lawKnowledge);

    LawKnowledge selectNextLawKnowledge(LawKnowledge paramLawKnowledge);

    int updatePv();
}
