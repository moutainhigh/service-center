package com.shengsu.website.home.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Bell on 2019/9/18.
 */
@Data
public class WheelCenterDeleteVo implements Serializable {
    @NotNull
    private Long id;
}
