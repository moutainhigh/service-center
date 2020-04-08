package com.shengsu.website.home.po;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Bell on 2019/9/20.
 */
@Data
public class WheelCenterListPo implements Serializable {
    private Long id;
    private String pictureOssId;
    private Integer type;
    private String url;
    private Double weight;
}
