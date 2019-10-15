package com.shengsu.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zyc on 2019/10/14.
 */
@Data
public class UserCreateVo implements Serializable {

    @NotBlank
    private String userName;
    @NotBlank
    private String realName;
    @NotNull
    private Short userType;
    @NotBlank
    private String pwd;
    private String descri;
    private String creator;
    private String mobile;
    private Short gender;
    @NotBlank
    private String tel;
    private String email;
    private String iconOssResourceId;
    private String org;
}
