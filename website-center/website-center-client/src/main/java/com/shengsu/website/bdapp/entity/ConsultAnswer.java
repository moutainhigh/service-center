package com.shengsu.website.bdapp.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-11 11:27
 **/
@Data
public class ConsultAnswer extends BaseEntity {
    @NotNull
    private String questionContent;
    /**
     * 回复人id
     */
    @NotNull
    private String replyLawyerId;

    /**
     * 回复内容
     */
    @NotNull
    private String replyContent;

}
