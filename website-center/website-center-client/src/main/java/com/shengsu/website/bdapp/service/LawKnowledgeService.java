package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.vo.LawKnowledgeDetailsVo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;

public interface LawKnowledgeService extends BaseService<LawKnowledge,String> {
    ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo);
    int updatePv(String knowledgeId);
    ResultBean getDetails(LawKnowledgeDetailsVo lawKnowledgeDetailsVo);
    ResultBean getLatestThree();
    ResultBean getRandomTitles();
}
