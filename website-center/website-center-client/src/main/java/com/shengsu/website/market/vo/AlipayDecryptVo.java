package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-09 16:14
 **/
@Data
public class AlipayDecryptVo implements Serializable {
    @NotBlank
    private String response;
    private String systemTag;
    private String origin;
    private String source;
}
