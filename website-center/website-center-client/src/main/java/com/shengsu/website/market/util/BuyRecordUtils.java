package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.BuyRecord;
import com.shengsu.website.market.po.BuyRecordPagePo;
import com.shengsu.website.market.po.BuyRecordPo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.shengsu.website.constant.ConsultConst.*;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 17:29
 **/
public class BuyRecordUtils {
    public static BuyRecordPo toBuyRecordPo(List<BuyRecord> buyRecords){
        BuyRecordPo buyRecordPo = new BuyRecordPo();
        Date now = new Date();
        for(BuyRecord buyRecord : buyRecords){
            Date date = buyRecord.getExpireTime();
            if(CONTRACT_LIBRARY.equals(buyRecord.getBuyType())){
                buyRecordPo.setContractServiceStatus(date.getTime()- now.getTime() > 0 ? VALID:EXPIRED);
            }
            if(HUMAN_RESOURCE.equals(buyRecord.getBuyType())){
                buyRecordPo.setHumanResourceServiceStatus(date.getTime()- now.getTime() > 0 ? VALID:EXPIRED);
            }
            if(CLOUD_LEGAL.equals(buyRecord.getBuyType())){
                buyRecordPo.setCloudLegalServiceStatus(date.getTime()- now.getTime() > 0 ? VALID:EXPIRED);
            }
        }
        if(null == buyRecordPo.getContractServiceStatus()){
            buyRecordPo.setContractServiceStatus(NOTPURCHASED);
        }
        if(null == buyRecordPo.getHumanResourceServiceStatus()){
            buyRecordPo.setHumanResourceServiceStatus(NOTPURCHASED);
        }
        if(null == buyRecordPo.getCloudLegalServiceStatus()){
            buyRecordPo.setCloudLegalServiceStatus(NOTPURCHASED);
        }
        return buyRecordPo;
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
