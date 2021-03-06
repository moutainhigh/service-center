package com.shengsu.bench.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by zyc on 2019/9/23.
 */
@Data
public class BenchLawyerCreateVo {

    @NotBlank
    private String headOssId;
    @NotBlank
    private String portraitOssId;
    @NotBlank
    private String name;
    @NotBlank
    private String field;
    @NotBlank
    private String majorExperience;

    private String position;

    @NotNull
    private Integer isGoldMedal;

    private Integer isTop;

    private Double weight;



    private List<Integer> typeList;

    private List<String> representativeCaseList;

}
