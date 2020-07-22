package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.BuyRecord;
import com.shengsu.website.market.po.BuyRecordPagePo;
import com.shengsu.website.market.po.BuyRecordPo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 17:29
 **/
public class BuyRecordUtils {
    public static List<BuyRecordPo> toBuyRecordPos(List<BuyRecord> buyRecords){
        List<BuyRecordPo> buyRecordPos = new ArrayList<>();
        for(BuyRecord buyRecord : buyRecords){
        BuyRecordPo buyRecordPo = new BuyRecordPo();
        Date now = new Date();
        Date date = buyRecord.getExpireTime();
        buyRecordPo.setRecordId(buyRecord.getRecordId());
        buyRecordPo.setWechatOpenid(buyRecord.getWechatOpenid());
        buyRecordPo.setOrderNo(buyRecord.getOrderNo());
        buyRecordPo.setBuyType(buyRecord.getBuyType());
        buyRecordPo.setBuyTime(buyRecord.getBuyTime());
        buyRecordPo.setIsExpired(date.getTime() - now.getTime() > 0 ? "Valid":"Expired");
        buyRecordPos.add(buyRecordPo);
        }
        return buyRecordPos;
    }
    public static List<BuyRecordPagePo> toBuyRecordPagePo(List<BuyRecord> buyRecords){
        List<BuyRecordPagePo> buyRecordPagePos = new ArrayList<>();
        for(BuyRecord buyRecord : buyRecords){
            BuyRecordPagePo buyRecordPagePo = new BuyRecordPagePo();
            buyRecordPagePo.setRecordId(buyRecord.getRecordId());
            buyRecordPagePo.setWechatOpenid(buyRecord.getWechatOpenid());
            buyRecordPagePo.setOrderNo(buyRecord.getOrderNo());
            buyRecordPagePo.setBuyType(buyRecord.getBuyType());
            buyRecordPagePo.setBuyTime(buyRecord.getBuyTime());
            buyRecordPagePo.setCreateTime(buyRecord.getCreateTime());
            buyRecordPagePo.setModifyTime(buyRecord.getModifyTime());
            buyRecordPagePo.setExpireTime(buyRecord.getExpireTime());
            buyRecordPagePos.add(buyRecordPagePo);
        }
        return buyRecordPagePos;
    }
}
