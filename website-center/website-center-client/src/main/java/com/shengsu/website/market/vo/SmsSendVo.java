package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2019-12-24 10:57
 **/
@Data
public class SmsSendVo implements Serializable {
    @NotBlank
    private String tel;
    private String smsCode;
    private String companyTag;
}
