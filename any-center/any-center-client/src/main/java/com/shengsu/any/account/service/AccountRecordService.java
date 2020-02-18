package com.shengsu.any.account.service;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.account.vo.AccountDetailListByPageVo;
import com.shengsu.any.account.vo.BalanceChangeRecordVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:22
 **/
public interface AccountRecordService extends BaseService<AccountRecord,String> {
    ResultBean listIncome(String token);
    ResultBean listExpend(String token);
    ResultBean listAccountDetailByPage(AccountDetailListByPageVo accountDetailListByPageVo);
    List<TotalIncomePo> totalIncome(Map<String,String> userId);
    List<TotalExpendPo> totalExpend(Map<String,String> userId);
    void create(String userId, BigDecimal beforeBalance, BigDecimal afterBalance,BigDecimal amount,String actionType);
    ResultBean getBalanceChangeRecord(BalanceChangeRecordVo balanceChangeRecordVo);
}
