package com.shengsu.website.home.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class NewsCenterListPageVo extends BaseEntity {
    private Integer type;
    private String title;
    private String source;
    private String author;
    private String ascription;
}
