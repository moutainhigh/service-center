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

    public static List<AccountListPo> toAccountListPos(List<User> users, Map<String,TotalIncomePo> totalIncomePoMap, Map<String,TotalExpendPo> totalExpendPoMap, Map<String,Account> accountMap) {
        if (users != null) {
            List<AccountListPo> accountListPos = new ArrayList<>();
            for (User user : users) {
                accountListPos.add(toAccountListPo(user,totalIncomePoMap,totalExpendPoMap,accountMap));
            }
            return accountListPos;
        }
        return null;
    }

    private static AccountListPo toAccountListPo(User user, Map<String,TotalIncomePo> totalIncomePoMap, Map<String,TotalExpendPo> totalExpendPoMap, Map<String,Account> accountMap) {
        if (user != null) {
            AccountListPo accountRecordPo = new AccountListPo();
            accountRecordPo.setUserId(user.getUserId());
            accountRecordPo.setTel(user.getTel());
            accountRecordPo.setCreateTime(user.getCreateTime());
            accountRecordPo.setIncome(totalIncomePoMap.get(user.getUserId())==null?new BigDecimal(0):totalIncomePoMap.get(user.getUserId()).getTotalIncome());
            accountRecordPo.setExpend(totalExpendPoMap.get(user.getUserId())==null?new BigDecimal(0):totalExpendPoMap.get(user.getUserId()).getTotalExpend());
            accountRecordPo.setAccountBalance(accountMap.get(user.getUserId())==null?new BigDecimal(0):accountMap.get(user.getUserId()).getBalance());
            return accountRecordPo;
        }
        return null;
    }

    public static List<RichesListPo> toRichesListPos(List<User> users, Map<String,TotalIncomePo> totalIncomePoMap, Map<String,Account> accountMap) {
        if (users != null) {
            List<RichesListPo> richesListPos = new ArrayList<>();
            for (User user : users) {
                richesListPos.add(toRichesListPo(user,totalIncomePoMap,accountMap));
            }
            return richesListPos;
        }
        return null;
    }

    private static RichesListPo toRichesListPo(User user, Map<String,TotalIncomePo> totalIncomePoMap, Map<String,Account> accountMap) {
        if (user != null) {
            RichesListPo richesListPo = new RichesListPo();
            richesListPo.setUserId(user.getUserId());
            richesListPo.setTel(user.getTel());
            richesListPo.setRealName(user.getRealName());
            richesListPo.setProvinceCode(user.getProvinceCode());
            richesListPo.setCityCode(user.getCityCode());
            richesListPo.setDistrictCode(user.getDistrictCode());
            richesListPo.setCreateTime(user.getCreateTime());
            richesListPo.setIncome(totalIncomePoMap.get(user.getUserId())==null?new BigDecimal(0):totalIncomePoMap.get(user.getUserId()).getTotalIncome());
            richesListPo.setAccountBalance(accountMap.get(user.getUserId())==null?new BigDecimal(0):accountMap.get(user.getUserId()).getBalance());
            return richesListPo;
        }
        return null;
    }
}
