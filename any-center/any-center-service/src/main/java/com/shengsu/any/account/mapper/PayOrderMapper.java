package com.shengsu.any.account.mapper;

import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder,String> {
}
