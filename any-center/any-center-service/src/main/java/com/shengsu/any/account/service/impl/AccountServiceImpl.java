package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.mapper.AccountMapper;
import com.shengsu.any.account.po.AccountListPo;
import com.shengsu.any.account.po.RichesListPo;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.util.AccountUtils;
import com.shengsu.any.account.vo.AccounListByPageVo;
import com.shengsu.any.account.vo.BalanceChangeVo;
import com.shengsu.any.account.vo.RichesListByPageVo;
import com.shengsu.any.app.constant.BizConst;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.message.constant.MessageConst;
import com.shengsu.any.message.entity.Message;
import com.shengsu.any.message.service.MessageService;
import com.shengsu.any.message.util.MessageUtils;
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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:02
 **/
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<Account, String> implements AccountServcie,BizConst,MessageConst {
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRecordService accountRecordService;
    @Autowired
    private MessageService messageService;
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
        Map<String,String> userIdMap = new HashMap<>();
        userIdMap.put("userId",userId);
        // 统计用户总收入
        List<TotalIncomePo> totalIncomePos = accountRecordService.totalIncome(userIdMap);
        // 统计用户总支出
        List<TotalExpendPo> totalExpendPos = accountRecordService.totalExpend(userIdMap);
        // 获取用户余额
        List<Account> accounts = accountMapper.getAllAccountBalance(userIdMap);
        Map<String, TotalIncomePo> totalIncomePoMap = AccountUtils.toTotalIncomeMap(totalIncomePos);
        Map<String, TotalExpendPo> totalExpendPoMap = AccountUtils.toTotalExpendMap(totalExpendPos);
        Map<String, Account> accountMap = AccountUtils.toAccountMap(accounts);

        Map<String,String> telMap = new HashMap<>();
        telMap.put("tel",accounListByPageVo.getTel());
        int totalCount = userService.countAccountAll(telMap);

        Map<String, Object> map = new HashMap<>();
        if (totalCount > 0) {
            List<User> users = userService.listAccountByPage(accounListByPageVo);
            Map<String, User> userMap = UserUtils.toUserMap(users);
            List<AccountListPo> accountListPos = AccountUtils.toAccountListPos(users,totalIncomePoMap,totalExpendPoMap,accountMap);
            map.put("root", accountListPos);
            map.put("totalCount", totalCount);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    @Override
    public ResultBean listRichesByPage(RichesListByPageVo richesListByPageVo) {
        String userId = null;
        if (StringUtils.isNotBlank(richesListByPageVo.getTel())){
            userId = userService.getUserIdByTel(richesListByPageVo.getTel());
        }
        Map<String,String> param = new HashMap<>();
        param.put("userId",userId);
        // 统计用户总收入
        List<TotalIncomePo> totalIncomePos = accountRecordService.totalIncome(param);
        // 获取用户余额
        List<Account> accounts = accountMapper.getAllAccountBalance(param);
        Map<String, TotalIncomePo> totalIncomePoMap = AccountUtils.toTotalIncomeMap(totalIncomePos);
        Map<String, Account> accountMap = AccountUtils.toAccountMap(accounts);

        Map<String,String> telMap = new HashMap<>();
        telMap.put("tel",richesListByPageVo.getTel());
        int totalCount = userService.countRichesAll(telMap);
        Map<String, Object> map = new HashMap<>();
        if (totalCount > 0) {
            List<User> users = userService.listRichesByPage(richesListByPageVo);
            Map<String, User> userMap = UserUtils.toUserMap(users);
            List<RichesListPo> richesListPos = AccountUtils.toRichesListPos(users,totalIncomePoMap,accountMap);
            map.put("root", richesListPos);
            map.put("totalCount", totalCount);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    @Override
    public ResultBean changeBalance(BalanceChangeVo balanceChangeVo) {
        String userId = balanceChangeVo.getUserId();
        BigDecimal beforeBalance = accountMapper.getAccountBalance(userId)==null?new BigDecimal(0):accountMapper.getAccountBalance(userId);
        BigDecimal amount = balanceChangeVo.getAmount();
        BigDecimal afterBalance = new BigDecimal(0);
        String actionType = balanceChangeVo.getActionType();
        String messageContent = "";
        String source = "";
        if(ACCOUNT_ACTION_TYPE_RECHARGE.equals(actionType)){
            afterBalance = beforeBalance.add(amount);
            messageContent = MessageFormat.format(MESSAGE_CONTENT_ACCOUNT_BALANCE_RECHARGE, amount);
            source = ACCOUNT_BALANCE_SOURCE_RECHANGE;
        }
        if (ACCOUNT_ACTION_TYPE_CASH_OUT.equals(actionType)){
            if (beforeBalance.compareTo(amount)<0){
                return ResultUtil.formResult(false, ResultCode.EXCEPTION_ACCOUNT_INSUFFICIENT_BALANCE);
            }
            afterBalance =  beforeBalance.subtract(amount);
            messageContent = MessageFormat.format(MESSAGE_CONTENT_ACCOUNT_BALANCE_CASH_OUT, amount);
            source = ACCOUNT_BALANCE_SOURCE_CASH_OUT;
        }
        // 保存账户提现记录
        String creator = balanceChangeVo.getCreator();
        String remark = balanceChangeVo.getRemark();
        accountRecordService.create(beforeBalance,afterBalance,source,balanceChangeVo);

        // 账户操作- 如果没有这个账户 则添加 否则修改
        Account oldAccount = accountMapper.getByUserId(userId);
        if (oldAccount== null){
            // 添加账户余额
            Account newAccount = new Account();
            newAccount.setAccountId(UUID.randomUUID().toString());
            newAccount.setUserId(userId);
            newAccount.setBalance(afterBalance);
            save(newAccount);
        }else{
            // 修改账户余额
            Account account = new Account();
            account.setUserId(userId);
            account.setBalance(afterBalance);
            accountMapper.update(account);
        }
        // 发送消息
        Message message = MessageUtils.toMessage(userId);
        message.setMessageContent(messageContent);
        messageService.save(message);

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public BigDecimal getAccountBalanceByUserId(String userId) {
        return accountMapper.getAccountBalance(userId);
    }


}
