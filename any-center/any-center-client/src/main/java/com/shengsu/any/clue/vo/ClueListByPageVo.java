package com.shengsu.any.clue.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ClueListByPageVo  extends BaseEntity {
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String tel;// 联系电话
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date onshelfStartTime;// 上架开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date onshelfEndTime;// 上架结束时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date offshelfStartTime;// 下架开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date offshelfEndTime;// 下架结束时间
}
