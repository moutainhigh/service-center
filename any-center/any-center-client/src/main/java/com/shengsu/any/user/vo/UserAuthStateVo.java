package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-17 09:45
 **/
@Data
public class UserAuthStateVo implements Serializable {
    @NotBlank
    private String userId;
}
