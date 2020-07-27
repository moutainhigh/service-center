package com.shengsu.trade.pay.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LvshifuOrderVo implements Serializable {
    @NotBlank
    private String buyType;// 购买类型

}
