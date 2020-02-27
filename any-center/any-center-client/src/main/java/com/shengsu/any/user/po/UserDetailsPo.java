package com.shengsu.any.user.po;

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
    private Short gender;
    private String tel;
    private String iconUrl;
    private String idCard;// 身份证
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String lawFirm;// 律所
    private String lawyerLicenseNo;// 执照号
    private String idCardFrontOssResourceId;// 身份证正面图片资源 Id
    private String idCardBackOssResourceId;// 身份证反面面图片资源 Id
    private String licenseOssResourceId;// 执照图片资源 Id
    private String idCardFrontUrl;// 身份证正面图片资源 url
    private String idCardBackUrl;// 身份证反面面图片资源 url
    private String licenseUrl;// 执照图片资源 url
    private String field;// 擅长领域
    private String fieldStr;// 擅长领域
    private String authState;// 用户状态:未认证,认证中,已认证,已拒绝
    private String authStateStr;// 用户状态名称:未认证,认证中,已认证,已拒绝
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT,timezone = "GMT+8")
    private Date createTime;

}
