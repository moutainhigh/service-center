package com.shengsu.any.clue.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

@Data
public class ClueEditVo implements Serializable {
    private String clueId;
    @NotBlank
    private String provinceCode;// 省级编码
    @NotBlank
    private String cityCode;// 市级编码
    @NotBlank
    private String districtCode;// 区级编码
    @NotBlank
    private String appellation;// 称谓
    @NotBlank
    private String clueType;// 线索类型
    @NotBlank
    private String customerDemands;// 客户诉求
    @NotBlank
    private String tel;// 联系电话
    @NotBlank
    private String cluePrice;// 线索价格
    @Null
    private String clueState;//线索状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Null
    private Date onshelfTime;// 上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Null
    private Date offshelfTime;// 下架时间
}
