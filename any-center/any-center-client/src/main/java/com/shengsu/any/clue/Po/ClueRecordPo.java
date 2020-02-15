package com.shengsu.any.clue.Po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-14 17:39
 **/
@Data
public class ClueRecordPo implements Serializable {
    private String recordId;// 记录id
    private String content;// 内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;//创建时间
}
