package com.shengsu.bench.po;

import lombok.Data;

import java.util.List;

/**
 * Created by zyc on 2019/9/20.
 */
@Data
public class BenchLawyerQueryPo {


    private Long id;

    private String name;

    private String field;

    private String majorExperience;

    private String position;

    private Double weight;

    private Integer isGoldMedal;

    private String headOssId;

    private String headOssUrl;

    private String portraitOssId;

    private String portraitOssUrl;

    private List<Integer> typeList;

    private List<String> representativeCaseList;
}
