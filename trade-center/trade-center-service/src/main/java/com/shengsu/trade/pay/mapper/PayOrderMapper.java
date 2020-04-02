package com.shengsu.trade.pay.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.trade.pay.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder,String> {
    int updateOrder(PayOrder payOrder);

    PayOrder getByOrderNo(String orderNo);
}
