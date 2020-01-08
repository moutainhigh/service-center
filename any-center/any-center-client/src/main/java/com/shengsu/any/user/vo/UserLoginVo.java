package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserLoginVo implements Serializable {
    @NotBlank
    private String tel;
    @NotBlank
    private String smsCode;
}
