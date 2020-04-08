package com.shengsu.website.home.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zyc on 2019/9/20.
 */
@Data
public class WheelCenterListVo implements Serializable {
    @NotNull
    private Integer type;
}
