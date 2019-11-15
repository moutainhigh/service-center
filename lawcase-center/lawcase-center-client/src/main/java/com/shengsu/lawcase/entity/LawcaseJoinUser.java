package com.shengsu.lawcase.entity;

import lombok.Data;

/**
 * Created by zyc on 2019/11/15.
 */
@Data
public class LawcaseJoinUser {
    private String userId;
    private String userName;
    private String realName;
    private Integer delFlag;
}
