package com.shengsu.helper.entity;

import com.shengsu.base.entity.BaseEntity;
import com.shengsu.helper.constant.AntiDuplicateEnum;
import lombok.Data;

/**
 * @author zxh
 * @ClassName: AntiDuplicate
 * @Description: 防重复vo
 * @date 2018-10-30
 */
@Data
public class AntiDuplicate extends BaseEntity {
    private static final long serialVersionUID = -4232424294297523592L;
    private String duplicateId;
    private String id;
    private AntiDuplicateEnum duplicateType;
}
