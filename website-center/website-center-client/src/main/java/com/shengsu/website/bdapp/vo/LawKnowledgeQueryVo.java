package com.shengsu.website.bdapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 13:49
 **/
@Data
public class LawKnowledgeQueryVo implements Serializable {
    @NotBlank
    private String knowledgeId;
}
