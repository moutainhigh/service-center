package com.shengsu.website.bdapp.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LawKnowledgeCategory extends BaseEntity{
    private static final long serialVersionUID = 1L;
    private String categoryId;
    private String categoryName;
    private String parentId;
    private Integer sortNum;//排序
    private List<LawKnowledgeCategory> children = new ArrayList<>();//子菜单列表
}
