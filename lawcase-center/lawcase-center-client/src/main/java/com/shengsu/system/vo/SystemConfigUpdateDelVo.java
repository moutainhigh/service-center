package com.shengsu.system.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zyc on 2019/11/15.
 */
@Data
public class SystemConfigUpdateDelVo implements Serializable {
    @NotBlank
    private String userId;
    @NotNull
    private Integer delFlag;
    @NotNull
    private Integer type;
}
