package com.shengsu.website.bdapp.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;


/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 10:52
 **/
@Data
public class LawKnowledgeListPageVo extends BaseEntity {
    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;
}
