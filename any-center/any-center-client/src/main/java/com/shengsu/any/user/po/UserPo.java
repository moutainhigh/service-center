package com.shengsu.any.user.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-16 11:40
 **/
@Data
public class UserPo implements Serializable {
    private Short gender;// 性别
    private String tel;// 手机号
    private String idCard;// 身份证
    private String lawFirm;// 律所
    private String lawyerLicenseNo;// 执照号
    private String field;// 擅长领域
    private String realName;
}
