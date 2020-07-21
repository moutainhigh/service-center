package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.BuyRecord;
import com.shengsu.website.market.po.BuyRecordPo;

import java.util.Date;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 17:29
 **/
public class BuyRecordUtils {
    public static BuyRecordPo toBuyRecordPo(BuyRecord buyRecord){
        BuyRecordPo buyRecordPo = new BuyRecordPo();
        Date now = new Date();
        Date date = buyRecord.getExpireTime();
        buyRecordPo.setRecordId(buyRecord.getRecordId());
        buyRecordPo.setWechatOpenid(buyRecord.getWechatOpenid());
        buyRecordPo.setOrderNo(buyRecord.getOrderNo());
        buyRecordPo.setBuyType(buyRecord.getBuyType());
        buyRecordPo.setBuyTime(buyRecord.getBuyTime());
        if (date.getTime() - now.getTime() > 0) {
            buyRecordPo.setIsExpired("Valid");
        }else {
            buyRecordPo.setIsExpired("Expired");
        }
        return buyRecordPo;
    }
}
