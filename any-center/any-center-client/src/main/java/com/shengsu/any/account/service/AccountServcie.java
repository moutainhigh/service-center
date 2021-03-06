package com.shengsu.any.account.service;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.po.AccountBalancePo;
import com.shengsu.any.account.vo.AccounListByPageVo;
import com.shengsu.any.account.vo.BalanceChangeVo;
import com.shengsu.any.account.vo.RichesListByPageVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.math.BigDecimal;


/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 09:59
 **/
public interface AccountServcie extends BaseService<Account,String> {
    ResultBean getAccountBalance(String token);

    ResultBean listAccountByPage(AccounListByPageVo accounListByPageVo);

    ResultBean listRichesByPage(RichesListByPageVo richesListByPageVo);

    ResultBean changeBalance(BalanceChangeVo balanceChangeVo);

    BigDecimal getAccountBalanceByUserId(String userId);

    void updateBalanceByUserId(String userId, BigDecimal balance);

    void create(String userId, BigDecimal bigDecimal);

    Account getByUserId(String userId);

    void updateBalanceByOrderNo(String payType,String orderNo, BigDecimal amount);
}
