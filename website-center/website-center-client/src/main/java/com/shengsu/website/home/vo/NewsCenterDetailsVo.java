package com.shengsu.website.home.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class NewsCenterDetailsVo implements Serializable {

    @NotNull
    private Long id;

    private Integer type;
}
