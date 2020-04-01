package com.shengsu.website.home.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zyc on 2019/10/12.
 */
@Data
public class NewsCenterGetOssUrl implements Serializable {

    @NotBlank
    private String ossId;
    @NotBlank
    private String fileDir;
}
