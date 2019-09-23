package com.shengsu.bench.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zyc on 2019/9/23.
 */
@Data
public class BenchLawyerCreateVo {

    @NotBlank
    private String portraitOssId;
    @NotBlank
    private String name;
    @NotBlank
    private String field;
    @NotBlank
    private String majorExperience;

    private String representativeCase;
    @NotNull
    private Integer isGoldMedal;
    @NotNull
    private Integer isTop;

    private Double weight;

}
