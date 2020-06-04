package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BaiduOrderVo implements Serializable {
    // 付款金额
    @NotBlank
    private String amount;
    private String systemTag;
}
