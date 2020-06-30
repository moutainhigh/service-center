package com.shengsu.website.market.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SystemConfig extends BaseEntity {
    private BigDecimal onlineConsultFee;
    private BigDecimal onlineConsultFeeOld;
    private BigDecimal telConsultFee;
    private BigDecimal telConsultFeeOld;
}