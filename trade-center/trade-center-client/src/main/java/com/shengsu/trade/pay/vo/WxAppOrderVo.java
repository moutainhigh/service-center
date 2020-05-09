package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WxAppOrderVo implements Serializable {
    // 充值金额
    @NotBlank
    private String amount;
    @NotBlank
    private String openId;
    private String ipAddress;

}
