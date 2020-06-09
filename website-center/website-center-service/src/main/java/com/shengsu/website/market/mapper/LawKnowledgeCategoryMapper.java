package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.LawKnowledgeCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawKnowledgeCategoryMapper extends BaseMapper<LawKnowledgeCategory, String> {
    String getNameByCategoryId(String categoryId);

    List<LawKnowledgeCategory> getManyByThirdCategoryIds(List<String> thirdCategoryIds);

    LawKnowledgeCategory getByCategoryId(String categoryId);
}
