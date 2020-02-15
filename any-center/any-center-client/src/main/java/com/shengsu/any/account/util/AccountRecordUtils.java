package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.ExpendListPo;
import com.shengsu.any.account.po.IncomeListPo;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-15 14:10
 **/
public class AccountRecordUtils {
    public static IncomeListPo toIncomeListPo(AccountRecord accountRecord) {
        if (accountRecord != null) {
            IncomeListPo incomeListPo = new IncomeListPo();
            incomeListPo.setActionType(accountRecord.getActionType());
            incomeListPo.setAmount(accountRecord.getAmount());
            incomeListPo.setModifyTime(accountRecord.getModifyTime());
            return incomeListPo;
        }
        return null;
    }

    public static ExpendListPo toExpendListPo(AccountRecord accountRecord) {
        if (accountRecord != null) {
            ExpendListPo expendListPo = new ExpendListPo();
            expendListPo.setActionType(accountRecord.getActionType());
            expendListPo.setAmount(accountRecord.getAmount());
            expendListPo.setModifyTime(accountRecord.getModifyTime());
            return expendListPo;
        }
        return null;
    }
}
