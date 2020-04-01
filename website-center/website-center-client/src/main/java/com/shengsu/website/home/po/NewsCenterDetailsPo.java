package com.shengsu.website.home.po;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class NewsCenterDetailsPo implements Serializable {

    private NewsCenterQueryPo newsCenter;

    private NewsCenterPreviousPo previousNewCenter;

    private NewsCenterPreviousPo nextNewCenter;
}
