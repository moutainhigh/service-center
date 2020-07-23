package com.shengsu.website.market.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 11:18
 **/
@Data
public class QuestionReply extends BaseEntity {
    /**
     * 回复id
     */
    private String replyId;

    /**
     * 问题id
     */
    private String questionId;

    /**
     * 回复人id
     */
    private String replyLawyerId;

    /**
     * 回复内容
     */
    private String replyContent;

}
