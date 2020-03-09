package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.AccountListPo;
import com.shengsu.any.account.po.RichesListPo;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.user.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-02-17 14:02
 **/
public class AccountUtils {
    public static Map<String,Account> toAccountMap(List<Account> accounts) {
        if (accounts != null) {
            Map<String, Account> accountMap = new HashMap<>();
            for (Account account : accounts) {
                accountMap.put(account.getUserId(), account);
            }
            return accountMap;
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

    public static List<AccountListPo> toAccountListPos(List<Account> accounts, Map<String,TotalIncomePo> totalIncomePoMap, Map<String,TotalExpendPo> totalExpendPoMap, Map<String, User> userMap) {
        if (accounts != null) {
            List<AccountListPo> accountListPos = new ArrayList<>();
            for (Account account : accounts) {
                accountListPos.add(toAccountListPo(account,totalIncomePoMap,totalExpendPoMap,userMap));
            }
            return accountListPos;
        }
        return null;
    }

    private static AccountListPo toAccountListPo(Account account, Map<String,TotalIncomePo> totalIncomePoMap, Map<String,TotalExpendPo> totalExpendPoMap, Map<String, User> userMap) {
        if (account != null) {
            AccountListPo accountRecordPo = new AccountListPo();
            accountRecordPo.setUserId(account.getUserId());
            accountRecordPo.setTel(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getTel());
            accountRecordPo.setCreateTime(account.getCreateTime());
            accountRecordPo.setIncome(totalIncomePoMap.get(account.getUserId())==null?new BigDecimal(0):totalIncomePoMap.get(account.getUserId()).getTotalIncome());
            accountRecordPo.setExpend(totalExpendPoMap.get(account.getUserId())==null?new BigDecimal(0):totalExpendPoMap.get(account.getUserId()).getTotalExpend());
            accountRecordPo.setAccountBalance(account.getBalance());
            accountRecordPo.setRealName(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getRealName());
            return accountRecordPo;
        }
        return null;
    }

    public static List<RichesListPo> toRichesListPos(List<Account> accounts, Map<String,TotalIncomePo> totalIncomePoMap, Map<String, User> userMap) {
        if (accounts != null) {
            List<RichesListPo> richesListPos = new ArrayList<>();
            for (Account account : accounts) {
                richesListPos.add(toRichesListPo(account,totalIncomePoMap,userMap));
            }
            return richesListPos;
        }
        return null;
    }

    private static RichesListPo toRichesListPo(Account account, Map<String,TotalIncomePo> totalIncomePoMap, Map<String, User> userMap) {
        if (account != null) {
            RichesListPo richesListPo = new RichesListPo();
            richesListPo.setUserId(account.getUserId());
            richesListPo.setTel(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getTel());
            richesListPo.setRealName(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getRealName());
            richesListPo.setProvinceCode(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getProvinceCode());
            richesListPo.setCityCode(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getCityCode());
            richesListPo.setDistrictCode(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getDistrictCode());
            richesListPo.setCreateTime(account.getCreateTime());
            richesListPo.setIncome(totalIncomePoMap.get(account.getUserId())==null?new BigDecimal(0):totalIncomePoMap.get(account.getUserId()).getTotalIncome());
            richesListPo.setAccountBalance(account.getBalance());
            richesListPo.setLawFirm(userMap.get(account.getUserId())==null?"":userMap.get(account.getUserId()).getLawFirm());
            return richesListPo;
        }
        return null;
    }
}
