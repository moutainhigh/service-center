package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.BuyRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-21 14:19
 **/
@Mapper
public interface BuyRecordMapper extends BaseMapper<BuyRecord, String> {
    List<BuyRecord> listByWechatOpenid(String wechatOpenid);
}
