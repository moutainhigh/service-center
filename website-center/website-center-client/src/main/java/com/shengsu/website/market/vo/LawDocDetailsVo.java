package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 14:33
 **/
@Data
public class LawDocDetailsVo implements Serializable {
    @NotBlank
    private String docId;
}
