package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-24 17:39
 **/
@Data
public class UserBandVo implements Serializable {
    private String unionid;
    @NotBlank
    private String openid;
    @NotBlank
    private String tel;
    @NotBlank
    private String smsCode;
    @NotBlank
    private String nickname;
    private Short sex;
}
