package com.shengsu.any.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-18 10:29
 **/
@Data
public class UploadHeadImageVo implements Serializable {
    @NotBlank
    private String userId;
    @NotBlank
    private String iconOssResourceId;// 头像资源
    private String token;
}
