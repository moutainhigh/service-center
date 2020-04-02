package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WxOrderVo implements Serializable {
    // 充值金额
    @NotBlank
    private String amount;
    // 账户id
    @NotBlank
    private String accountId;
    private String openId;
    private String ipAddress;
}
