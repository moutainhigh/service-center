package com.shengsu.bench.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zyc on 2019/9/23.
 */
@Data
public class BenchLawyerListPageVo extends BaseEntity {


    private String name;

    private Integer isGoldMedal;

    private Integer type;

}
