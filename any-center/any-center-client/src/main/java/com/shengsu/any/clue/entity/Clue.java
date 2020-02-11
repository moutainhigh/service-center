package com.shengsu.any.clue.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description: 案件线索实体
 * @author: lipiao
 * @create: 2020-01-08 10:29
 **/
@Data
public class Clue extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String clueId;// 线索id
    private String clueCode;//线索号
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
    private Date createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date onshelfTime;// 上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date offshelfTime;// 下架时间

}
