package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WxMwebOrderVo implements Serializable {
    // 充值金额
    @NotBlank
    private String amount;
    private String systemTag;
    @NotBlank
    private String lawyerId;
    private String ipAddress;

}
