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
    private BigDecimal humanResourceFee;
    private BigDecimal humanResourceFeeOld;
    private BigDecimal contractFee;
    private BigDecimal contractFeeOld;
    private BigDecimal cloudLegalFee;
    private BigDecimal cloudLegalFeeOld;
}