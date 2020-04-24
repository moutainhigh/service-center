package com.shengsu.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-24 16:53
 **/
@Data
public class RoleEditVo implements Serializable {
    @NotBlank
    private String userId;
    @NotBlank
    private String userType;
}
