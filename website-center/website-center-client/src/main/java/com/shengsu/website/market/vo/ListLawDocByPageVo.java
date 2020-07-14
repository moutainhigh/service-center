package com.shengsu.website.market.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 14:41
 **/
@Data
public class ListLawDocByPageVo extends BaseEntity {
    private String docType;// 文档类型
    private String docName;// 文档名称
    private String fullName;// 文档全称
}
