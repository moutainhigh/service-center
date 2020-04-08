package com.shengsu.website.home.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Bell on 2019/9/18.
 */
@Data
public class WheelCenterUpdateVo implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String pictureOssId;
    @NotNull
    private Integer type;
    private String url;
    private Double weight;
}