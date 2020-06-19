package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AliAppTelConsultOrderVo implements Serializable {
    // 电话
    @NotBlank
    private String tel;
    // 法律领域
    @NotBlank
    private String lawField;
    // 买家id
    @NotBlank
    private String buyerId;
    // 充值金额
    @NotBlank
    private String amount;
    @NotBlank
    private String systemTag;

}
