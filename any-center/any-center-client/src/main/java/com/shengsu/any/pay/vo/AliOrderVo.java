package com.shengsu.any.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AliOrderVo implements Serializable {
    // 充值金额
    @NotBlank
    private String amount;
    // 账户id
    @NotBlank
    private String accountId;

}
