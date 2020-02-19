package com.shengsu.any.clue.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ClueVo implements Serializable {
    private String clueId;
    @NotBlank
    private String provinceCode;// 省级编码
    @NotBlank
    private String cityCode;// 市级编码
    @NotBlank
    private String districtCode;// 区级编码
    @NotBlank
    private String appellation;// 称谓
    @NotBlank
    private String clueType;// 线索类型
    @NotBlank
    private String customerDemands;// 客户诉求
    @NotBlank
    private String tel;// 联系电话
    @NotBlank
    private BigDecimal cluePrice;// 线索价格
}
