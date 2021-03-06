package com.shengsu.any.account.mapper;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.vo.AccounListByPageVo;
import com.shengsu.any.account.vo.RichesListByPageVo;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:01
 **/
@Mapper
public interface AccountMapper extends BaseMapper<Account,String> {
    BigDecimal getAccountBalance(String userId);

    Account getByUserId(String userId);

    int countAccountAll(AccounListByPageVo accounListByPageVo);

    List<Account> listAccountByPage(AccounListByPageVo accounListByPageVo);

    int countRichesAll(RichesListByPageVo richesListByPageVo);

    List<Account> listRichesByPage(RichesListByPageVo richesListByPageVo);

    void updateBalanceByAccountId(Account account);

    void updateBalanceByUserId(Account account);
}
