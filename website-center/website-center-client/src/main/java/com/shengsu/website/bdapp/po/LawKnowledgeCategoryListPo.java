package com.shengsu.website.bdapp.po;

import com.shengsu.website.bdapp.entity.LawKnowledgeCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 17:04
 **/
@Data
public class LawKnowledgeCategoryListPo implements Serializable {
    private String categoryId;
    private String categoryName;
    private String parentId;
    private Integer sortNum;//排序
    private List<LawKnowledgeCategoryListPo> children = new ArrayList<>();//子菜单列表
}
