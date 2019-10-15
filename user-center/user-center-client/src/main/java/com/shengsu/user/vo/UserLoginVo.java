package com.shengsu.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zyc on 2019/10/14.
 */
@Data
public class UserLoginVo implements Serializable {

    @NotBlank
    private String userName;
    @NotBlank
    private String pwd;
}
