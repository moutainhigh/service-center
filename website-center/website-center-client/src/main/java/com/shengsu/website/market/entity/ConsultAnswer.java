package com.shengsu.website.market.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-11 11:27
 **/
@Data
public class ConsultAnswer extends BaseEntity {
    @NotBlank
    private String questionContent;
    /**
     * 回复人id
     */
    @NotBlank
    private String replyLawyerId;

    /**
     * 回复内容
     */
    @NotBlank
    private String replyContent;

}
