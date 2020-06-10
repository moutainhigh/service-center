package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AliMarketOrderVo implements Serializable {
    // 充值金额
    @NotBlank
    private String amount;
    @NotBlank
    private String verifyCode;
    @NotBlank
    private String systemTag;

}
