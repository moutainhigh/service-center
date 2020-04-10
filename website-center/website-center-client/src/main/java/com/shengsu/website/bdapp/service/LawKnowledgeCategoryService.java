package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.LawKnowledgeCategory;

public interface LawKnowledgeCategoryService extends BaseService<LawKnowledgeCategory,String> {
    ResultBean listAllCategory();
}
