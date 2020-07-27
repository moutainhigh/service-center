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
    @NotBlank
    private String systemTag;
    private String consultTag;// 市场推广在线咨询和电话咨询区分
    private TelConsultVo telConsultVo;// 电话咨询参数
    private LvshifuOrderVo lvshifuOrderVo;

}
