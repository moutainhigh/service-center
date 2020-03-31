package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.*;
import com.shengsu.any.account.vo.BalanceChangeVo;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.user.entity.User;
import com.shengsu.user.entity.AliasUser;

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
            expendListPo.setClueId(accountRecord.getClueId().substring(0,17));
            expendListPo.setActionType(accountRecord.getActionType());
            expendListPo.setAmount(accountRecord.getAmount());
            expendListPo.setModifyTime(accountRecord.getModifyTime());
            return expendListPo;
        }
        return null;
    }
    public static AccountRecordDetailsPo toAccountRecordDetailsPo(AccountRecord accountRecord,Map<String, User> userMap,Map<String, String> inOrOutMap) {
        if (accountRecord != null) {
            AccountRecordDetailsPo detailsPo = new AccountRecordDetailsPo();
            detailsPo.setTel(userMap.get(accountRecord.getUserId())==null?"":userMap.get(accountRecord.getUserId()).getTel());
            detailsPo.setActionType(accountRecord.getActionType());
            detailsPo.setAmount(accountRecord.getAmount());
            detailsPo.setSource(accountRecord.getSource());
            detailsPo.setCreateTime(accountRecord.getCreateTime());
            detailsPo.setRealName(userMap.get(accountRecord.getUserId())==null?"":userMap.get(accountRecord.getUserId()).getRealName());
            detailsPo.setInOrOutType(inOrOutMap.get(accountRecord.getActionType()));
            return detailsPo;
        }
        return null;
    }
    public static List<AccountRecordDetailsPo> toAccountRecordDetailsPos(List<AccountRecord> accountRecords,Map<String, User> userMap, Map<String, String> inOrOutMap) {
        if (accountRecords != null) {
            List<AccountRecordDetailsPo> accountRecordDetailsPos = new ArrayList<>();
            for (AccountRecord accountRecord : accountRecords) {
                accountRecordDetailsPos.add(toAccountRecordDetailsPo(accountRecord,userMap,inOrOutMap));
            }
            return accountRecordDetailsPos;
        }
        return null;
    }


    public static AccountRecord toAccountRecord(BigDecimal beforeBalance, BigDecimal afterBalance,String source,BalanceChangeVo balanceChangeVo) {
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setRecordId(UUID.randomUUID().toString());
        accountRecord.setClueId(balanceChangeVo.getClueId());
        accountRecord.setUserId(balanceChangeVo.getUserId());
        accountRecord.setAmount(balanceChangeVo.getAmount());
        accountRecord.setBeforeBalance(beforeBalance);
        accountRecord.setAfterBalance(afterBalance);
        accountRecord.setCreator(balanceChangeVo.getCreator());
        accountRecord.setRemark(balanceChangeVo.getRemark());
        accountRecord.setSource(source);
        accountRecord.setActionType(balanceChangeVo.getActionType());
        return accountRecord;
    }

    public static BalanceChangeRecordPo toBalanceChangeRecordPo(AccountRecord accountRecord,Map<String, AliasUser> aliasUserMap) {
        if (accountRecord != null) {
            BalanceChangeRecordPo cashOutRecordPo = new BalanceChangeRecordPo();
            cashOutRecordPo.setBeforeBalance(accountRecord.getBeforeBalance());
            cashOutRecordPo.setAfterBalance(accountRecord.getAfterBalance());
            cashOutRecordPo.setCreateTime(accountRecord.getCreateTime());
            cashOutRecordPo.setActionType(accountRecord.getActionType());
            cashOutRecordPo.setCreator(aliasUserMap.get(accountRecord.getCreator())==null?"":aliasUserMap.get(accountRecord.getCreator()).getRealName());
            cashOutRecordPo.setRemark(accountRecord.getRemark());
            return cashOutRecordPo;
        }
        return null;
    }

    public static List<BalanceChangeRecordPo> toBalanceChangeRecordPos(List<AccountRecord> accountRecords, Map<String, AliasUser> aliasUserMap) {
        if (accountRecords != null) {
            List<BalanceChangeRecordPo> balanceChangeRecordPos = new ArrayList<>();
            for (AccountRecord accountRecord : accountRecords) {
                balanceChangeRecordPos.add(toBalanceChangeRecordPo(accountRecord, aliasUserMap));
            }
            return balanceChangeRecordPos;
        }
        return null;
    }

    public static BalanceChangeVo toBalanceChangeVo(Clue clue, String clueId, String userId, String buy_clue) {
        BalanceChangeVo balanceChangeVo = new BalanceChangeVo();
        balanceChangeVo.setClueId(clueId);
        balanceChangeVo.setUserId(userId);
        balanceChangeVo.setAmount(clue.getCluePrice());
        balanceChangeVo.setActionType(buy_clue);
        return balanceChangeVo;
    }
}
