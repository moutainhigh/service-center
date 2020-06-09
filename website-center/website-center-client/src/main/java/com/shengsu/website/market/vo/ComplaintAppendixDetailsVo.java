package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 14:06
 **/
@Data
public class ComplaintAppendixDetailsVo implements Serializable {
    @NotBlank
    private String complaintId;
    private String idCardFlag;
    private String ortFlag;
    private String enterpriseFlag;
    private String trademarkFlag;
}
