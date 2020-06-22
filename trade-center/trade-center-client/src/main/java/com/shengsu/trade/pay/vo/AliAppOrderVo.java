package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AliAppOrderVo implements Serializable {
    // 买家id
    @NotBlank
    private String buyerId;
    // 充值金额
    @NotBlank
    private String amount;
    @NotBlank
    private String systemTag;
    @NotBlank
    private String consultTag;
    private AliTelConsultOrderVo telConsultOrderVo;

}
