package com.shengsu.any.account.service;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.account.vo.AccountDetailListByPageVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:22
 **/
public interface AccountRecordService extends BaseService<AccountRecord,String> {
    ResultBean listIncome(String token);
    ResultBean listExpend(String token);
    ResultBean listAccountDetailByPage(AccountDetailListByPageVo accountDetailListByPageVo);
    List<TotalIncomePo> totalIncome(String userId);
    List<TotalExpendPo> totalExpend(String userId);
}
