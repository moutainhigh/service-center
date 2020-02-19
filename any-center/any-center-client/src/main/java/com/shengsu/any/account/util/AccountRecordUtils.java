package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.*;
import com.shengsu.any.user.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    public static AccountRecord toAccountRecord(String userId, BigDecimal beforeBalance, BigDecimal afterBalance,BigDecimal amount,String creator,String remark) {
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setRecordId(UUID.randomUUID().toString());
        accountRecord.setUserId(userId);
        accountRecord.setAmount(amount);
        accountRecord.setBeforeBalance(beforeBalance);
        accountRecord.setAfterBalance(afterBalance);
        accountRecord.setCreator(creator);
        accountRecord.setRemark(remark);
        return accountRecord;
    }

    public static BalanceChangeRecordPo toBalanceChangeRecordPo(AccountRecord accountRecord,Map<String, User> userMap) {
        if (accountRecord != null) {
            BalanceChangeRecordPo cashOutRecordPo = new BalanceChangeRecordPo();
            cashOutRecordPo.setBeforeBalance(accountRecord.getBeforeBalance());
            cashOutRecordPo.setAfterBalance(accountRecord.getAfterBalance());
            cashOutRecordPo.setCreateTime(accountRecord.getCreateTime());
            cashOutRecordPo.setCreator(userMap.get(accountRecord.getCreator()).getRealName());
            return cashOutRecordPo;
        }
        return null;
    }

    public static List<BalanceChangeRecordPo> toBalanceChangeRecordPos(List<AccountRecord> accountRecords,Map<String, User> userMap) {
        if (accountRecords != null) {
            List<BalanceChangeRecordPo> balanceChangeRecordPos = new ArrayList<>();
            for (AccountRecord accountRecord : accountRecords) {
                balanceChangeRecordPos.add(toBalanceChangeRecordPo(accountRecord,userMap));
            }
            return balanceChangeRecordPos;
        }
        return null;
    }
}
