package com.shengsu.any.clue.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClueListByPageVo  extends BaseEntity {
    private String provinceCode;// 省级编码
    private String cityCode;// 市级编码
    private String districtCode;// 区级编码
    private String tel;// 联系电话
    private String clueType;// 线索类型
    private String clueState;// 线索状态
    private String onshelfStartTime;// 上架开始时间
    private String onshelfEndTime;// 上架结束时间
    private String offshelfStartTime;// 下架开始时间
    private String offshelfEndTime;// 下架结束时间
}
