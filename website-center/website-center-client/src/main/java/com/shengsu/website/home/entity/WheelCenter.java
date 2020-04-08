package com.shengsu.website.home.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class WheelCenter extends BaseEntity{

    private Long id;
    private String pictureOssId;
    private Integer type;
    private String url;
    private Double weight;
}
