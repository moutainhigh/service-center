package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.LawKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawKnowledgeMapper extends BaseMapper<LawKnowledge, String> {
    LawKnowledge selectByKnowledgeId(String knowledgeId);

    LawKnowledge selectPreviousLawKnowledge(LawKnowledge lawKnowledge);

    LawKnowledge selectNextLawKnowledge(LawKnowledge paramLawKnowledge);

    int updatePv(String knowledgeId);

    List<LawKnowledge> getLatestThree();

    List<LawKnowledge> getAllTitle();

    Integer countThirdNotNull(LawKnowledge lawKnowledge);

    List<LawKnowledge> listPageThirdNotNull(LawKnowledge lawKnowledge);

    List<LawKnowledge> listPageSecondNotNull(LawKnowledge lawKnowledge);

    Integer countSecondNotNull(LawKnowledge lawKnowledge);

    List<LawKnowledge> listPageFirstNotNull(LawKnowledge lawKnowledge);

    Integer countFirstNotNull(LawKnowledge lawKnowledge);

    LawKnowledge selectThirdPreviousLawKnowledge(LawKnowledge paramLawKnowledge);

    LawKnowledge selectSecondPreviousLawKnowledge(LawKnowledge paramLawKnowledge);

    LawKnowledge selectFirstPreviousLawKnowledge(LawKnowledge paramLawKnowledge);

    LawKnowledge selectThirdNextLawKnowledge(LawKnowledge paramLawKnowledge);

    LawKnowledge selectSecondNextLawKnowledge(LawKnowledge paramLawKnowledge);

    LawKnowledge selectFirstNextLawKnowledge(LawKnowledge paramLawKnowledge);

    LawKnowledge selectByTitle(String title);

    Integer countFullTextSearch(LawKnowledge lawKnowledge);

    List<LawKnowledge> fullTextSearchListPage(LawKnowledge lawKnowledge);
}
