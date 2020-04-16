package com.shengsu.website.bdapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 14:00
 **/
@Data
public class FeedbackDeleteVo implements Serializable {
    @NotBlank
    private String feedbackId;
}
