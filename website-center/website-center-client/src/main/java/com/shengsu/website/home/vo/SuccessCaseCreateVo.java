package com.shengsu.website.home.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.util.DateUtil;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class SuccessCaseCreateVo implements Serializable {

    @NotBlank
    private String legendOssId;
    @NotNull
    private Integer type;
    @NotBlank
    private String title;
    @NotNull
    @JsonFormat(pattern = DateUtil.DEFAULT_DATETIME_FORMAT,timezone = "GMT+8")
    private Date caseDate;
    @NotBlank
    private String summary;
    @NotBlank
    private String source;
    @NotBlank
    private String author;
    @NotBlank
    private String content;

    private Integer isHomeShow;
    private List<Long> relevantIdList;
}
