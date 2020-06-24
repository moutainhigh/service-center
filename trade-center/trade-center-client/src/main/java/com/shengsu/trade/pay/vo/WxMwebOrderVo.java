package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WxMwebOrderVo implements Serializable {
    private String suffixReturnUrl;
    // 充值金额
    @NotBlank
    private String amount;
    @NotBlank
    private String systemTag;
    @NotBlank
    private String lawyerId;
    private String ipAddress;
    private String consultTag;
    private TelConsultVo telConsultVo;

}
