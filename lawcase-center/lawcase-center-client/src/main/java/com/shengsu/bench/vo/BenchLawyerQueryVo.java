package com.shengsu.bench.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by zyc on 2019/9/23.
 */
@Data
public class BenchLawyerQueryVo {

    @NotNull
    private Long id;
}
