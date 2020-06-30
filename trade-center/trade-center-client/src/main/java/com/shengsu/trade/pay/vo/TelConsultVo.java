package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class TelConsultVo implements Serializable {
    // 电话
    @NotBlank
    private String tel;
    // 法律领域
    @NotBlank
    private String lawField;
    @NotBlank
    private String source;

}
