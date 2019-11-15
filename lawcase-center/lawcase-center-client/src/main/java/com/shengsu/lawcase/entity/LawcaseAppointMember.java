package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description: 案件指派人实体
 * @author: lipiao
 * @create: 2019-09-26 15:26
 **/
@Data
public class LawcaseAppointMember extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String userId;// 指派人id
    private Integer delFlag;
}
