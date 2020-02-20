package com.shengsu.any.account.vo;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;


/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-17 14:47
 **/
@Data
public class AccounListByPageVo extends BaseEntity {
    private String tel;// 联系电话
    private String userId;
}
