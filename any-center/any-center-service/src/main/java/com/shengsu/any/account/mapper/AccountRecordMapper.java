package com.shengsu.any.account.mapper;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:23
 **/
@Mapper
public interface AccountRecordMapper extends BaseMapper<AccountRecord,String> {
}
