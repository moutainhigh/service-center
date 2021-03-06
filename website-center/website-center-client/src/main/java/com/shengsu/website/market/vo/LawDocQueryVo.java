package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 13:49
 **/
@Data
public class LawDocQueryVo implements Serializable {
    @NotBlank
    private String docId;
}
