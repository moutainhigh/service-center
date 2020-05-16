package com.shengsu.any.clue.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-16 13:22
 **/
@Data
public class CluePersonalVo implements Serializable{
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;//创建时间
    private String clueType;// 线索类型
    private List<String> clueIds;
}
