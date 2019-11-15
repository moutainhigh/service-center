package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

@Data
public class LawcaseApproveTrialMember extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String lawyerUserId;
    private String lawyerAssistantUserId;
    private String isCurrentNode;
    private int sort;
    private Integer delFlag;



}
