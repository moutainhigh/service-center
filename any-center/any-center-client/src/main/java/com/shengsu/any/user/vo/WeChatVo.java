package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by zyc on 2019/12/16.
 */
@Data
public class WeChatVo implements Serializable {
    @NotBlank
    private String code;
    private String state;
}
