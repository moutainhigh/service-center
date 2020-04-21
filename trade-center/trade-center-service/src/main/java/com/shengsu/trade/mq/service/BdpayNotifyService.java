package com.shengsu.trade.mq.service;

import com.alibaba.fastjson.JSON;
import com.shengsu.mq.MessageProcessor;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.service.BdpayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.BdPayNotifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import static com.shengsu.trade.app.constant.BizConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-03-26 10:40
 **/
@Slf4j
@Service("bdpayNotifyService")
public class BdpayNotifyService implements MessageProcessor<BdPayNotifyVo> {
    @Autowired
    private BdpayService bdpayService;
    @Override
    public boolean handleMessage(BdPayNotifyVo bdPayNotifyVo) {
        log.info("处理消息："+ bdPayNotifyVo);
        bdpayService.handleMessage(bdPayNotifyVo);
        return true;
    }
    @Override
    public Class<BdPayNotifyVo> getClazz() {
        return BdPayNotifyVo.class;
    }
}
