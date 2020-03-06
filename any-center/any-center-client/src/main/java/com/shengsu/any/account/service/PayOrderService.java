package com.shengsu.any.account.service;

import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.vo.PayOrderQueryVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-27 11:04
 **/
public interface PayOrderService extends BaseService<PayOrder,String> {
    ResultBean create(PayOrder payOrder);
    ResultBean updateOrder(PayOrder payOrder);
    PayOrder getByOrderNo(String orderNo);
    ResultBean listPage(PayOrder payOrder);
    ResultBean orderQuery(PayOrderQueryVo payOrderQueryVo)throws Exception;
}
