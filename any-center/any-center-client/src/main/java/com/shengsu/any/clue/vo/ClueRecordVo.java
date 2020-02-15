package com.shengsu.any.clue.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-15 11:31
 **/
@Data
public class ClueRecordVo implements Serializable {
    @NotBlank
    private String content;// 内容
    private String clueId;// 线索id
    private String userId;// 用户id
}
