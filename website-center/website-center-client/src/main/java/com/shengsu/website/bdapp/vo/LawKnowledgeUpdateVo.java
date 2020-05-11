package com.shengsu.website.bdapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 11:49
 **/
@Data
public class LawKnowledgeUpdateVo implements Serializable{
    @NotBlank
    private String knowledgeId;
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
