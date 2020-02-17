package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.*;
import com.shengsu.any.user.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static AccountRecordDetailsPo toAccountRecordDetailsPo(AccountRecord accountRecord,Map<String, User> userMap) {
        if (accountRecord != null) {
            AccountRecordDetailsPo detailsPo = new AccountRecordDetailsPo();
            detailsPo.setTel(userMap.get(accountRecord.getUserId()).getTel());
            detailsPo.setActionType(accountRecord.getActionType());
            detailsPo.setAmount(accountRecord.getAmount());
            detailsPo.setSource(accountRecord.getSource());
            detailsPo.setCreateTime(accountRecord.getCreateTime());
            return detailsPo;
        }
        return null;
    }
    public static List<AccountRecordDetailsPo> toAccountRecordDetailsPos(List<AccountRecord> accountRecords,Map<String, User> userMap) {
        if (accountRecords != null) {
            List<AccountRecordDetailsPo> accountRecordDetailsPos = new ArrayList<>();
            for (AccountRecord accountRecord : accountRecords) {
                accountRecordDetailsPos.add(toAccountRecordDetailsPo(accountRecord,userMap));
            }
            return accountRecordDetailsPos;
        }
        return null;
    }

    public static List<AccountRecordPo> toAccountRecordsPos(List<AccountRecord> accountRecords, Map<String, User> userMap, Map<String, TotalIncomePo> totalIncomePoMap, Map<String, TotalExpendPo> totalExpendPoMap, Map<String, Account> accountMap) {
        if (accountRecords != null) {
            List<AccountRecordPo> accountRecordPos = new ArrayList<>();
            for (AccountRecord accountRecord : accountRecords) {
                accountRecordPos.add(toAccountRecordPo(accountRecord,userMap,totalIncomePoMap,totalExpendPoMap,accountMap));
            }
            return accountRecordPos;
        }
        return null;
    }

    private static AccountRecordPo toAccountRecordPo(AccountRecord accountRecord, Map<String, User> userMap, Map<String, TotalIncomePo> totalIncomePoMap, Map<String, TotalExpendPo> totalExpendPoMap, Map<String, Account> accountMap) {
        if (accountRecord != null) {
            AccountRecordPo accountRecordPo = new AccountRecordPo();
            accountRecordPo.setUserId(accountRecord.getUserId());
            accountRecordPo.setTel(userMap.get(accountRecord.getUserId()).getTel()==null?"":userMap.get(accountRecord.getUserId()).getTel());
            accountRecordPo.setCreateTime(accountRecord.getCreateTime());
            accountRecordPo.setIncome(totalIncomePoMap.get(accountRecord.getUserId())==null?new BigDecimal(0):totalIncomePoMap.get(accountRecord.getUserId()).getTotalIncome());
            accountRecordPo.setExpend(totalExpendPoMap.get(accountRecord.getUserId())==null?new BigDecimal(0):totalExpendPoMap.get(accountRecord.getUserId()).getTotalExpend());
            accountRecordPo.setAccountBalance(accountMap.get(accountRecord.getUserId())==null?new BigDecimal(0):accountMap.get(accountRecord.getUserId()).getBalance());
            return accountRecordPo;
        }
        return null;
    }

    public static Map<String, TotalIncomePo> toTotalIncomeMap(List<TotalIncomePo> totalIncomePos) {
        if (totalIncomePos != null) {
            Map<String, TotalIncomePo> totalIncomePoMap = new HashMap<>();
            for (TotalIncomePo totalIncomePo : totalIncomePos) {
                totalIncomePoMap.put(totalIncomePo.getUserId(), totalIncomePo);
            }
            return totalIncomePoMap;
        }
        return null;
    }

    public static Map<String,TotalExpendPo> toTotalExpendMap(List<TotalExpendPo> totalExpendPos) {
        if (totalExpendPos != null) {
            Map<String, TotalExpendPo> totalExpendPoMap = new HashMap<>();
            for (TotalExpendPo totalExpendPo : totalExpendPos) {
                totalExpendPoMap.put(totalExpendPo.getUserId(), totalExpendPo);
            }
            return totalExpendPoMap;
        }
        return null;
    }
}
