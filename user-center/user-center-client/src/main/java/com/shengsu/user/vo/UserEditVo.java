package com.shengsu.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zyc on 2019/10/14.
 */
@Data
public class UserEditVo implements Serializable {

    @NotBlank
    private String userId;
    private String userName;
    @NotBlank
    private String realName;
    @NotNull
    private String userType;
    private String descri;
    private String creator;
    private String mobile;
    private Short gender;
    @NotBlank
    private String tel;
    private String email;
    private String iconOssResourceId;
    private String org;
    //制定刷新的token
    private String token;
}
