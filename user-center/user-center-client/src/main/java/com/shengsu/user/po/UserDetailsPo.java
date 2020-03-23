package com.shengsu.user.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zyc on 2019/8/26.
 */
@Data
public class UserDetailsPo implements Serializable{
    private String userId;
    private String userName;
    private String realName;
    private String userType;
    private String descri;
    private Short gender;
    private String tel;
    private String email;
    private String mobile;
    private String iconOssResourceId;
    private String org;
    private String iconUrl;

    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT,timezone = "GMT+8")
    private Date createTime;

}
