package com.shengsu.any.clue.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ClueVo implements Serializable {
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String appellation;// 称谓
    private String clueType;// 线索类型
    private String customerDemands;// 客户诉求
    private String tel;// 联系电话
    private String cluePrice;// 线索价格
    private String clueState;// 线索状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date onshelf_time;// 上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date offshelf_time;// 下架时间
}
