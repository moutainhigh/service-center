package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.BuyRecord;

public interface BuyRecordService extends BaseService<BuyRecord,String> {
    ResultBean create(BuyRecord buyRecord);
    ResultBean buyListByPage(BuyRecord buyRecord);
    ResultBean getByWechatOpenid(String wechatOpenid);

    ResultBean saveBuyRecord(String openId, String outTradeNo, String buyType);
}
