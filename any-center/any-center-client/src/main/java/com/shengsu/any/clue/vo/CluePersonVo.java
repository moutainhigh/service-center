package com.shengsu.any.clue.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-16 14:18
 **/
@Data
public class CluePersonVo implements Serializable {
    private List<String> clueId;
    private List<Date> createTime;
}
