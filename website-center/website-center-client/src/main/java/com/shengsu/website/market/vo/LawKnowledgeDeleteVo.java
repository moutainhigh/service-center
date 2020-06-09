package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-05-11 11:26
 **/
@Data
public class LawKnowledgeDeleteVo implements Serializable {
    @NotBlank
    private String knowledgeId;
}
