package com.shengsu.website.bdapp.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 14:41
 **/
@Data
public class LawKnowledgeListByPageVo extends BaseEntity {
    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;
    private String title;
}
