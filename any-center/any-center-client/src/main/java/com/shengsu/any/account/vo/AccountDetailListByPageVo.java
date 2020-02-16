package com.shengsu.any.account.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-16 13:25
 **/
@Data
public class AccountDetailListByPageVo extends BaseEntity {
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String tel;// 联系电话
    private String source;// 来源
    private String userId;
}
