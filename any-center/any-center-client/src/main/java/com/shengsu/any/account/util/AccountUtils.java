package com.shengsu.any.account.util;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.po.TotalIncomePo;

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
}
