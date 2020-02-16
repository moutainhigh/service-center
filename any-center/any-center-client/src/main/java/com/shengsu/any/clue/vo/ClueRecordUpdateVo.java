package com.shengsu.any.clue.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-16 17:36
 **/
@Data
public class ClueRecordUpdateVo implements Serializable {
    @NotBlank
    private String recordId;// 记录id
    private String clueId;// 线索id
    @NotBlank
    private String content;// 内容
    private String userId;// 用户id
}
