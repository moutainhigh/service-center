package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.mapper.AccountMapper;
import com.shengsu.any.account.po.AccountListPo;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.util.AccountRecordUtils;
import com.shengsu.any.account.util.AccountUtils;
import com.shengsu.any.account.vo.AccounListByPageVo;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:02
 **/
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<Account, String> implements AccountServcie {
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRecordService accountRecordService;
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public BaseMapper<Account, String> getBaseMapper() {
        return accountMapper;
    }

    @Override
    public ResultBean getAccountBalance(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        BigDecimal accountBalance = accountMapper.getAccountBalance(user.getUserId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("accountBalance", accountBalance==null?new BigDecimal(0):accountBalance);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean listAccountByPage(AccounListByPageVo accounListByPageVo) {
        String userId = null;
        if (StringUtils.isNotBlank(accounListByPageVo.getTel())){
            userId = userService.getUserIdByTel(accounListByPageVo.getTel());
        }
        // 统计用户总收入
        List<TotalIncomePo> totalIncomePos = accountRecordService.totalIncome(userId);
        // 统计用户总支出
        List<TotalExpendPo> totalExpendPos = accountRecordService.totalExpend(userId);
        // 获取用户余额
        List<Account> accounts = accountMapper.getAllAccountBalance(userId);
        Map<String, TotalIncomePo> totalIncomePoMap = AccountUtils.toTotalIncomeMap(totalIncomePos);
        Map<String, TotalExpendPo> totalExpendPoMap = AccountUtils.toTotalExpendMap(totalExpendPos);
        Map<String, Account> accountMap = AccountUtils.toAccountMap(accounts);

        Map<String, Object> map = new HashMap<>();
        int totalCount = userService.countAccountAll(accounListByPageVo.getTel());
        if (totalCount > 0) {
            List<User> users = userService.listAccountByPage(accounListByPageVo);
            Map<String, User> userMap = UserUtils.toUserMap(users);
            List<AccountListPo> accountListPos = AccountUtils.toAccountListPos(users,totalIncomePoMap,totalExpendPoMap,accountMap);
            map.put("root", accountListPos);
            map.put("totalCount", totalCount);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }


}
