package com.shengsu.any.account.mapper;

import com.shengsu.any.account.entity.Account;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:01
 **/
@Mapper
public interface AccountMapper extends BaseMapper<Account,String> {
}
