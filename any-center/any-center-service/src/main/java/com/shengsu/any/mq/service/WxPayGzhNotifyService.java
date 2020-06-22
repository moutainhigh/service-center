package com.shengsu.any.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.mq.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.shengsu.any.app.constant.BizConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-03-26 10:40
 **/
@Slf4j
@Service("wxPayGzhNotifyService")
public class WxPayGzhNotifyService implements MessageProcessor<JSONObject> {
    @Autowired
    private AccountServcie accountServcie;
    @Override
    public boolean handleMessage(JSONObject jsonObject) {
        log.info("处理消息："+ jsonObject);
        String outTradeNo = jsonObject.getString("outTradeNo");
        if (outTradeNo.contains("WGTN")){
            // 修改账户余额 并添加账户余额记录
            BigDecimal amount = jsonObject.getBigDecimal("amount");
            accountServcie.updateBalanceByOrderNo(PAY_TYPE_WECHAT,outTradeNo,amount);
        }
        return true;
    }
    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }
}
