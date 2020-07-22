package com.shengsu.website.market.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.entity.BuyRecord;
import com.shengsu.website.market.mapper.BuyRecordMapper;
import com.shengsu.website.market.po.BuyRecordPagePo;
import com.shengsu.website.market.po.BuyRecordPo;
import com.shengsu.website.market.service.BuyRecordService;
import com.shengsu.website.market.util.BuyRecordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 14:17
 **/
@Service("buyRecordSercice")
public class BuyRecordServiceImpl extends BaseServiceImpl<BuyRecord, String> implements BuyRecordService {
    @Autowired
    private BuyRecordMapper buyRecordMapper;
    @Override
    public BaseMapper<BuyRecord, String> getBaseMapper() {
        return buyRecordMapper;
    }
    @Override
    public ResultBean create(BuyRecord buyRecord) {
        buyRecord.setRecordId(UUID.randomUUID().toString());
        buyRecordMapper.save(buyRecord);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }
    @Override
    public ResultBean buyListByPage(BuyRecord buyRecord){
        List<BuyRecord> buyRecords = buyRecordMapper.listByPage(buyRecord);
        int totalCount = buyRecordMapper.countAll(buyRecord);
        List<BuyRecordPagePo> buyRecordPagePos = new ArrayList<>();
        if(buyRecords.size() != 0){
            buyRecordPagePos = BuyRecordUtils.toBuyRecordPagePo(buyRecords);
        }
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, buyRecordPagePos, totalCount);
    }
    @Override
    public ResultBean getByWechatOpenid(String wechatOpenid){
        List<BuyRecord> buyRecords = buyRecordMapper.listByWechatOpenid(wechatOpenid);
        if(buyRecords.size()==0){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, buyRecords);
        }
        List<BuyRecordPo> buyRecordPos = BuyRecordUtils.toBuyRecordPos(buyRecords);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, buyRecordPos);
    }
}
