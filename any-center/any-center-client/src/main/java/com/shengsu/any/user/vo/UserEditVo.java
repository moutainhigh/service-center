package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserEditVo implements Serializable {
    @NotBlank
    private String userId;
    @NotBlank
    private String realName;// 真实姓名
    @NotNull
    private Short gender;// 性别
    private String iconOssResourceId;// 头像资源
    @NotBlank
    private String idCard;// 身份证
    @NotBlank
    private String lawFirm;// 律所
    @NotBlank
    private String lawyerLicenseNo;// 执照号
    @NotBlank
    private String idCardFrontOssResourceId;// 身份证正面图片资源
    @NotBlank
    private String idCardBackOssResourceId;// 身份证反面面图片资源
    @NotBlank
    private String licenseOssResourceId;// 执照图片资源
    private String field;// 擅长领域
    @NotBlank
    private String provinceCode;// 省级编码
    @NotBlank
    private String cityCode;// 市级编码
    @NotBlank
    private String districtCode;// 区级编码
    private String token;
}
