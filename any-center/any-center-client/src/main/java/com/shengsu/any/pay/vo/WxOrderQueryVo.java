package com.shengsu.any.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WxOrderQueryVo implements Serializable {
    // 商户订单号
   @NotBlank
   private String orderNo;

}
