package com.shengsu.any.clue.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-26 18:11
 **/
@Data
public class AxbUnBindRequestVo implements Serializable {
    @NotBlank
    private String bindId;
}
