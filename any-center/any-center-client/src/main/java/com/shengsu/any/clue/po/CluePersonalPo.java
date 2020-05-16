package com.shengsu.any.clue.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.any.user.po.UserPo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-05-16 10:30
 **/
@Data
public class CluePersonalPo implements Serializable {
    private String clueId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date onshelfTime;
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String clueType;// 线索类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date buyTime;
    private String appellation;
    private BigDecimal cluePrice;
    private UserPo userPo;
}
