package com.shengsu.website.home.po;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyc on 2019/9/19.
 */
@Data
public class WheelCenterQueryPo implements Serializable {

    private Long id;
    private String pictureOssId;
    private Integer type;
    private String url;
    private Double weight;
}
