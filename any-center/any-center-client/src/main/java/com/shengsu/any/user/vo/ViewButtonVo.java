package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-13 15:48
 **/
@Data
public class ViewButtonVo implements Serializable {
    @NotBlank
    private String type;
    @NotBlank
    private String name;
    @NotBlank
    private String url;

}
