package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @author lipiao
 * @ClassName: LawcaseGroupPermanentMember
 * @Description: 案件组永驻人员（实体类）
 */
@Data
public class LawcaseGroupPermanentMember extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String memberId;
    private String userId;
    private Integer delFlag;
}
