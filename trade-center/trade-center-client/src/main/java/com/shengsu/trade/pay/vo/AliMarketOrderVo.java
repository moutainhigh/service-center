package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AliMarketOrderVo implements Serializable {
    private String suffixReturnUrl;
    // 充值金额
    @NotBlank
    private String amount;
    @NotBlank
    private String systemTag;
    private String lawyerId;
    private String consultTag;
    // 电话咨询参数
    private TelConsultVo telConsultVo;

}
