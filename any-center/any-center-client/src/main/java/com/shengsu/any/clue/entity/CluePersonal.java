package com.shengsu.any.clue.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description: 个人线索实体
 * @author: lipiao
 * @create: 2020-01-08 10:57
 **/
@Data
public class CluePersonal extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String clueId;// 线索id
    private String userId;// 用户id
}
