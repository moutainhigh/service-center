package com.shengsu.trade.pay.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.trade.app.constant.BizConst;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.mapper.PayOrderMapper;
import com.shengsu.trade.pay.po.PayOrderListPo;
import com.shengsu.trade.pay.service.AlipayService;
import com.shengsu.trade.pay.service.BdpayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.service.WxpayService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.PayOrderQueryVo;
import com.shengsu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-27 11:06
 **/
@Service("payOrderService")
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrder, String> implements PayOrderService,BizConst {
    @Autowired
    private SystemDictService systemDictService;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private WxpayService wxpayService;
    @Autowired
    private BdpayService bdpayService;
    @Override
    public BaseMapper<PayOrder, String> getBaseMapper() {
        return payOrderMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultBean create(PayOrder payOrder) {
        save(payOrder);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean updateOrder(PayOrder payOrder) {
        payOrderMapper.updateOrder(payOrder);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public PayOrder getByOrderNo(String orderNo) {
        return payOrderMapper.getByOrderNo(orderNo);
    }

    @Override
    public ResultBean listPage(PayOrder payOrder) {
        Map<String, Object> map = new HashMap<>();
        payOrder.setSearch(StringUtil.ToLikeStr(payOrder.getSearch()));
        int totalCount = payOrderMapper.countAll(payOrder);
        if (totalCount > 0) {
            List<PayOrder> payOrders = payOrderMapper.listByPage(payOrder);
            Map<String, SystemDict> systemDictMap = systemDictService.mapByDictCode("order_status");
            List<PayOrderListPo> payOrderListPos = PayOrderUtils.toPayOrderListPos(payOrders,systemDictMap);
            map.put("root", payOrderListPos);
            map.put("totalCount", totalCount);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    @Override
    public ResultBean orderQuery(PayOrderQueryVo payOrderQueryVo)throws Exception{
        if(PAY_TYPE_ALIPAY.equals(payOrderQueryVo.getPayType())){
            return alipayService.orderQuery(payOrderQueryVo.getOrderNo());
        }else if(PAY_TYPE_WECHAT.equals(payOrderQueryVo.getPayType())&&PAY_SUB_TYPE_WECHAT_GZH.equals(payOrderQueryVo.getPaySubType())){
            // 微信公众号支付
            return wxpayService.orderQuery(payOrderQueryVo.getOrderNo(),ORDER_FLAG_WECHAT_GZH);
        }else if(PAY_TYPE_WECHAT.equals(payOrderQueryVo.getPayType())&&PAY_SUB_TYPE_WECHAT_WEAPP.equals(payOrderQueryVo.getPaySubType())){
            // 微信小程序支付
            return wxpayService.orderQuery(payOrderQueryVo.getOrderNo(),ORDER_FLAG_WECHAT_WEAPP);
        }else{
            return bdpayService.orderQuery(payOrderQueryVo.getOrderNo());
        }
    }
}
