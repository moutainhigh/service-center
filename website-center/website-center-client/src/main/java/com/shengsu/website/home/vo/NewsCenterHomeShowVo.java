package com.shengsu.website.home.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class NewsCenterHomeShowVo implements Serializable {
    @NotBlank
    private String ascription;// 新闻归属

}
