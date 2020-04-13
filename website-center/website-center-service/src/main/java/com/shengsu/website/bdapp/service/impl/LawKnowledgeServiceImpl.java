package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.mapper.LawKnowledgeMapper;
import com.shengsu.website.bdapp.service.LawKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 18:03
 **/
@Service("lawKnowledgeService")
public class LawKnowledgeServiceImpl extends BaseServiceImpl<LawKnowledge, String> implements LawKnowledgeService {
    @Autowired
    private LawKnowledgeMapper lawKnowledgeMapper;
    @Override
    public BaseMapper<LawKnowledge, String> getBaseMapper() {
        return lawKnowledgeMapper;
    }
}
