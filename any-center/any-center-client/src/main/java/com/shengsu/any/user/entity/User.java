package com.shengsu.any.user.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:01
 **/
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String userId;// 用户id
    private String userName;// 用户姓名
    private String realName;// 真实姓名
    private String iconOssResourceId;// 头像资源
    private String iconUrl;
    private Short gender;// 性别
    private String idCard;// 身份证
    private String tel;// 手机号
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String lawFirm;// 律所
    private String lawyerLicenseNo;// 执照号
    private String idCardFrontOssResourceId;// 身份证正面图片资源
    private String idCardBackOssResourceId;// 身份证反面面图片资源
    private String licenseOssResourceId;// 执照图片资源
    private String field;// 擅长领域
    private String authState;// 用户状态:未认证,认证中,已认证,已拒绝
    private String wechatUnionid;// 微信uninid
    private String wechatOpenid;// 微信openid

}
