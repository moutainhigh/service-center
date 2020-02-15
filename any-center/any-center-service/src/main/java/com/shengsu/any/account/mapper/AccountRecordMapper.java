package com.shengsu.any.account.mapper;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:23
 **/
@Mapper
public interface AccountRecordMapper extends BaseMapper<AccountRecord,String> {
    List<AccountRecord> listIncome(String userId);

    List<AccountRecord> listExpend(String userId);
}
