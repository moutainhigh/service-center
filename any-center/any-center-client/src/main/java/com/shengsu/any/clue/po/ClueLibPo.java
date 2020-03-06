package com.shengsu.any.clue.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ClueLibPo implements Serializable {
    private String clueId;// 线索id
    private String clueCode;//线索号
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String appellation;// 称谓
    private String clueType;// 线索类型
    private String customerDemands;// 客户诉求
    private String tel;// 联系电话
    private BigDecimal cluePrice;// 线索价格
    private String clueState;// 线索状态
    private String telX;//虚拟号码
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date bindTime;//虚拟号绑定时间
    private Integer expiration;//虚拟号时效
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date onshelfTime;// 上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date offshelfTime;// 下架时间
}
