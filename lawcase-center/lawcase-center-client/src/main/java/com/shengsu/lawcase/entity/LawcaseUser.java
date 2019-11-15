package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @ClassName: User
 * @Description: 用户（实体类）
 * @author zxh
 * @date 2018-8-16
 * 
 */
@Data
public class LawcaseUser extends BaseEntity {
    private static final long serialVersionUID = 6253394804472191324L;
    private String userId;
    private String userName;
    private String realName;
    private String iconOssResourceId;

}