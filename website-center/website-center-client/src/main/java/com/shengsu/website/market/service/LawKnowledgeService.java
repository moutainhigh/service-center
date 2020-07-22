package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.LawKnowledge;
import com.shengsu.website.market.vo.*;

public interface LawKnowledgeService extends BaseService<LawKnowledge,String> {
    ResultBean create(LawKnowledgeCreateVo lawKnowledgeCreateVo);
    ResultBean remove(LawKnowledgeDeleteVo lawKnowledgeDeleteVo);
    ResultBean edit(LawKnowledgeUpdateVo lawKnowledgeUpdateVo);
    ResultBean query(LawKnowledgeQueryVo lawKnowledgeQueryVo);
    ResultBean queryWithKeyWord(LawKnowledgeQueryVo lawKnowledgeQueryVo);
    ResultBean listKnowledgeByPage(LawKnowledgeListByPageVo lawKnowledgeListByPageVo);
    ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo);
    int updatePv(String knowledgeId);
    ResultBean getDetails(LawKnowledgeDetailsVo lawKnowledgeDetailsVo);
    ResultBean getLatestThree(SystemTagVo systemTagVo);
    ResultBean getRandomTitles(SystemTagVo systemTagVo);
    ResultBean fullTextSearchListPage(FullTextSearchListPageVo fullTextSearchListPageVo);
    ResultBean esContentFieldListByPage(EsListByPageVo esListByPageVo);
    ResultBean esManyFieldsListByPage(LawKnowledgeListByPageVo lawKnowledgeListByPageVo);
    ResultBean esContentFieldListByPageSort(EsListByPageVo esListByPageVo);
}
