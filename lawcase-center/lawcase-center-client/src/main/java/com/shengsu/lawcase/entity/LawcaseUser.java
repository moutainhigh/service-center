package com.shengsu.lawcase.entity;

import com.shengsu.base.entity.BaseEntity;

/**
 * @ClassName: User
 * @Description: 用户（实体类）
 * @author zxh
 * @date 2018-8-16
 * 
 */
public class LawcaseUser extends BaseEntity {
    private static final long serialVersionUID = 6253394804472191324L;
    private String userId;
    private String userName;
    private String realName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}