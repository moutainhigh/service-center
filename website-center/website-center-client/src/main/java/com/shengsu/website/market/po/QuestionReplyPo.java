package com.shengsu.website.market.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 13:57
 **/
@Data
public class QuestionReplyPo implements Serializable {
    /**
     * 用户id
     */
    private String lawyerId;

    /**
     * 真实姓名
     */
    private String lawyerName;

    /**
     * 头像资源
     */
    private String iconOssResourceUrl;
    private String questionId;
    private String questionContent;
    /**
     * 回复内容
     */
    private String replyContent; /**
     * 回复id
     */
    private String replyId;

}
