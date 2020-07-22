package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.LawKnowledgeCategory;
import com.shengsu.website.market.vo.ListCategoryVo;

import java.util.List;

public interface LawKnowledgeCategoryService extends BaseService<LawKnowledgeCategory,String> {
    ResultBean listCategory(ListCategoryVo listCategoryVo);

    String getNameByCategoryId(String categoryId);

    List<LawKnowledgeCategory> getManyByThirdCategoryIds(List<String> thirdCategoryIds);

    LawKnowledgeCategory getByCategoryId(String categoryId);
}
