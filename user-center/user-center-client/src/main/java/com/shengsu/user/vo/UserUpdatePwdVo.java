package com.shengsu.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zyc on 2019/10/14.
 */
@Data
public class UserUpdatePwdVo {

    @NotBlank
    private String userId;
    @NotBlank
    private String pwd;
}
