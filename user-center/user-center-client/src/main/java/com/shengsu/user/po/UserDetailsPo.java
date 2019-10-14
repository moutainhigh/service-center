package com.shengsu.user.po;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyc on 2019/8/26.
 */
@Data
public class UserDetailsPo implements Serializable{
    private String userId;
    private String userName;
    private String realName;
    private Short userType;
    private String descri;
    private Short gender;
    private String tel;
    private String email;
    private String mobile;
    private String iconOssResourceId;
    private String org;
    private String iconUrl;

}
