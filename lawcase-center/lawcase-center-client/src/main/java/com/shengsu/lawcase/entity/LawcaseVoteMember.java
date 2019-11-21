package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description: 案件终审实体
 * @author: lipiao
 * @create: 2019-09-26 15:26
 **/
@Data
public class LawcaseVoteMember extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String userId;// 终审用户id
    private Integer delFlag;
}
