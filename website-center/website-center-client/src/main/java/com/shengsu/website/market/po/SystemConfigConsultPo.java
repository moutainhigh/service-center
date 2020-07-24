package com.shengsu.website.market.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-23 18:50
 **/
@Data
public class SystemConfigConsultPo implements Serializable {
    private BigDecimal onlineConsultFee;
    private BigDecimal onlineConsultFeeOld;
    private BigDecimal telConsultFee;
    private BigDecimal telConsultFeeOld;
}
