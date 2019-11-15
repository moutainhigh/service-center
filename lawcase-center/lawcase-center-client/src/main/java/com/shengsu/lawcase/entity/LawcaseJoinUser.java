package com.shengsu.lawcase.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyc on 2019/11/15.
 */
@Data
public class LawcaseJoinUser implements Serializable{
    private String userId;
    private String userName;
    private String realName;
    private Integer delFlag;
}
