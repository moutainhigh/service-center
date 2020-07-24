package com.shengsu.website.market.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-23 18:54
 **/
@Data
public class SystemConfigCloudLegalPo implements Serializable {
    private BigDecimal humanResourceFee;
    private BigDecimal humanResourceFeeOld;
    private BigDecimal contractFee;
    private BigDecimal contractFeeOld;
    private BigDecimal cloudLegalFee;
    private BigDecimal cloudLegalFeeOld;
}
