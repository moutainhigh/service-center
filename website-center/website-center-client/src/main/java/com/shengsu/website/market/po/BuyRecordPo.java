package com.shengsu.website.market.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 17:25
 **/
@Data
public class BuyRecordPo implements Serializable {
    private String cloudLegalServiceStatus;

    private String humanResourceServiceStatus;

    private String contractServiceStatus;

}
