package com.shengsu.bench.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * Created by zyc on 2019/9/20.
 */
@Data
public class BenchLawyer extends BaseEntity {

    private Long id;

    private String headOssId;

    private String headOssUrl;

    private String portraitOssId;

    private String portraitOssUrl;

    private String name;

    private String field;

    private String majorExperience;

    private String  position;

    private String representativeCase;

    private Integer isGoldMedal;

    private Integer isTop;

    private Double weight;

    private String types;

}
