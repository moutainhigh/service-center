package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 14:41
 **/
@Data
public class ListLawDocVo implements Serializable {
    @NotBlank
    private String docType;// 文档类型
    @NotBlank
    private String docSubtype; // 文档子类型
}
