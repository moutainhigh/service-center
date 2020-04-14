package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawKnowledgeMapper extends BaseMapper<LawKnowledge, String> {
    LawKnowledge selectByKnowledgeId(String knowledgeId);

    LawKnowledge selectPreviousLawKnowledge(LawKnowledge lawKnowledge);

    LawKnowledge selectNextLawKnowledge(LawKnowledge paramLawKnowledge);

    int updatePv(String knowledgeId);

    List<LawKnowledge> getlatestThreeCount();

    List<String> getAllTitle();
}
