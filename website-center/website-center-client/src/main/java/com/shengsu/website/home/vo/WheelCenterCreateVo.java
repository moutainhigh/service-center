package com.shengsu.website.home.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Bell on 2019/9/17.
 */
@Data
public class WheelCenterCreateVo implements Serializable {
    @NotBlank
    private String pictureOssId;
    @NotNull
    private Integer type;
    private String url;
    private Double weight;
}
