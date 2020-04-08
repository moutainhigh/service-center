package com.shengsu.website.home.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class SuccessCaseQueryVo implements Serializable {
    @NotNull
    private Long id;
    public SuccessCaseQueryVo() {
    }
    public SuccessCaseQueryVo(Long id) {
        this.id = id;
    }
}
