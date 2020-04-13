package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;

public interface LawKnowledgeService extends BaseService<LawKnowledge,String> {
    ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo);
}
