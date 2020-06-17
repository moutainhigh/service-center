package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-10 14:40
 **/
@Data
public class AlipayVo implements Serializable {
    @NotBlank
    private String code;
    private String systemTag;
}
