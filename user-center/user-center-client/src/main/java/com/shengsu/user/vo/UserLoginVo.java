package com.shengsu.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
/**
 * Created by zyc on 2019/10/14.
 */
@Data
public class UserLoginVo {

    @NotBlank
    private String userName;
    @NotBlank
    private String pwd;
}
