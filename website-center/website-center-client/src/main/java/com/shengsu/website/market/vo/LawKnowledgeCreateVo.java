package com.shengsu.website.market.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
public class LawKnowledgeCreateVo implements Serializable {
    @NotBlank
    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateTime;
    @NotBlank
    private String source;
}
