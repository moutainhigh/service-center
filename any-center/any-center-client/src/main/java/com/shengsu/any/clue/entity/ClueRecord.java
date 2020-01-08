package com.shengsu.any.clue.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 11:47
 **/
@Data
public class ClueRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String recordId;// 记录id
    private String clueId;// 线索id
    private String content;// 内容
    private String userId;// 用户id
}
