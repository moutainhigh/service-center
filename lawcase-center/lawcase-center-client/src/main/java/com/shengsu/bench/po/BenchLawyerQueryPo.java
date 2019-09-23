package com.shengsu.bench.po;

import lombok.Data;

/**
 * Created by zyc on 2019/9/20.
 */
@Data
public class BenchLawyerQueryPo {


    private Long id;

    private String name;

    private String field;

    private String majorExperience;

    private String representativeCase;

    private Integer isGoldMedal;

    private Integer isTop;

    private String portraitOssId;

    private String portraitOssUrl;
}
