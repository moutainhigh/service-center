package com.shengsu.website.bdapp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-11 15:08
 **/
@Data
public class ConsultAnswerVo implements Serializable {
    private String questionContent;
    /**
     * 回复人id
     */
    private String questionId;
    /**
     * 回复内容
     */
    private String replyContent;
}
