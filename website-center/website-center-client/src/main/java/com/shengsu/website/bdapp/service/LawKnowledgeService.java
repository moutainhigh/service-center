package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.vo.*;

public interface LawKnowledgeService extends BaseService<LawKnowledge,String> {
    ResultBean create(LawKnowledgeCreateVo lawKnowledgeCreateVo);
    ResultBean remove(LawKnowledgeDeleteVo lawKnowledgeDeleteVo);
    ResultBean edit(LawKnowledgeUpdateVo lawKnowledgeUpdateVo);
    ResultBean query(LawKnowledgeQueryVo lawKnowledgeQueryVo);
    ResultBean listKnowledgeByPage(LawKnowledgeListByPageVo lawKnowledgeListByPageVo);
    ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo);
    int updatePv(String knowledgeId);
    ResultBean getDetails(LawKnowledgeDetailsVo lawKnowledgeDetailsVo);
    ResultBean getLatestThree();
    ResultBean getRandomTitles();
}
