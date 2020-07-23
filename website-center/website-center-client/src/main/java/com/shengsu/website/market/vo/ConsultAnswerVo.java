package com.shengsu.website.market.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-11 15:08
 **/
@Data
public class ConsultAnswerVo implements Serializable {
    @NotBlank
    private String questionContent;
    /**
     * 回复人id
     */
    @NotBlank
    private String questionId;
    /**
     * 回复内容
     */
    @NotBlank
    private String replyContent;
    @NotBlank
    private String replyLawyerId;
    private String systemTag;
}
