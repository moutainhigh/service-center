package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.entity.PayOrder;
import com.shengsu.any.account.mapper.AccountMapper;
import com.shengsu.any.account.po.AccountListPo;
import com.shengsu.any.account.po.RichesListPo;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.service.PayOrderService;
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
import java.util.*;

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
    private PayOrderService payOrderService;
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
        Account account = accountMapper.getByUserId(user.getUserId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("accountBalance", account==null||account.getBalance()==null?new BigDecimal(0):account.getBalance());
        resultMap.put("accountId", account==null||account.getAccountId()==null?"":account.getAccountId());
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean listAccountByPage(AccounListByPageVo accounListByPageVo) {
        String userId = null;
        if (StringUtils.isNotBlank(accounListByPageVo.getTel())){
            userId = userService.getUserIdByTel(accounListByPageVo.getTel());
        }
        accounListByPageVo.setUserId(userId);
        // 统计用户总收入
        List<TotalIncomePo> totalIncomePos = accountRecordService.totalIncome(accounListByPageVo);
        // 统计用户总支出
        List<TotalExpendPo> totalExpendPos = accountRecordService.totalExpend(accounListByPageVo);
        Map<String, TotalIncomePo> totalIncomePoMap = AccountUtils.toTotalIncomeMap(totalIncomePos);
        Map<String, TotalExpendPo> totalExpendPoMap = AccountUtils.toTotalExpendMap(totalExpendPos);

        int totalCount = accountMapper.countAccountAll(accounListByPageVo);
        Map<String, Object> map = new HashMap<>();
        if (totalCount > 0) {
            List<Account> accounts = accountMapper.listAccountByPage(accounListByPageVo);

            List<String> userIds = new ArrayList<>();
            for (Account account : accounts) {
                userIds.add(account.getUserId());
            }
            List<User> users = new ArrayList<>();
            if (null != userIds && userIds.size()>0){
                users = userService.getMany(userIds);
            }
            Map<String, User> userMap = UserUtils.toUserMap(users);

            List<AccountListPo> accountListPos = AccountUtils.toAccountListPos(accounts,totalIncomePoMap,totalExpendPoMap,userMap);
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
        richesListByPageVo.setUserId(userId);
        // 统计用户总收入
        List<TotalIncomePo> totalIncomePos = accountRecordService.historyRecharge(richesListByPageVo);
        Map<String, TotalIncomePo> totalIncomePoMap = AccountUtils.toTotalIncomeMap(totalIncomePos);
        int totalCount = accountMapper.countRichesAll(richesListByPageVo);
        Map<String, Object> map = new HashMap<>();
        if (totalCount > 0) {
            List<Account> accounts = accountMapper.listRichesByPage(richesListByPageVo);

            List<String> userIds = new ArrayList<>();
            for (Account account : accounts) {
                userIds.add(account.getUserId());
            }
            List<User> users = new ArrayList<>();
            if (null != userIds && userIds.size()>0){
                users = userService.getMany(userIds);
            }
            Map<String, User> userMap = UserUtils.toUserMap(users);

            List<RichesListPo> richesListPos = AccountUtils.toRichesListPos(accounts,totalIncomePoMap,userMap);
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
        accountRecordService.create(beforeBalance,afterBalance,source,balanceChangeVo);
        // 修改账户余额
        updateBalanceByUserId(userId,afterBalance);
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

    @Override
    public void updateBalanceByUserId(String userId, BigDecimal balance) {
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(balance);
        accountMapper.updateBalanceByUserId(account);
    }

    @Override
    public void create(String userId, BigDecimal balance) {
        Account account = new Account();
        account.setAccountId(UUID.randomUUID().toString());
        account.setUserId(userId);
        account.setBalance(balance);
        accountMapper.save(account);
    }

    @Override
    public Account getByUserId(String userId) {
       return accountMapper.getByUserId(userId);
    }

    @Override
    public void updateBalanceByOrderNo(String orderNo, BigDecimal amount) {
        PayOrder payOrder = payOrderService.getByOrderNo(orderNo);
        // 获取账户
        Account account = accountMapper.get(payOrder.getAccountId());
        // 获取账户余额
        BigDecimal beforeBalance = account.getBalance();
        // 充值后余额
        BigDecimal afterBalance = amount.add(account.getBalance());
        if (null!=account && null!=payOrder) {
            // 修改账户余额
            account.setAccountId(payOrder.getAccountId());
            account.setBalance(afterBalance);
            accountMapper.updateBalanceByAccountId(account);
            // 添加账户余额记录
            BalanceChangeVo balanceChangeVo = new BalanceChangeVo();
            balanceChangeVo.setUserId(account.getUserId());
            balanceChangeVo.setAmount(amount);
            balanceChangeVo.setActionType(ACCOUNT_ACTION_TYPE_H5_RECHARGE);
            accountRecordService.create(beforeBalance,afterBalance,ACCOUNT_BALANCE_SOURCE_WECHAT_RECHANGE,balanceChangeVo);
            // 发送消息
            Message message = MessageUtils.toMessage(account.getUserId());
            message.setMessageContent(MessageFormat.format(MESSAGE_CONTENT_ACCOUNT_BALANCE_RECHARGE, amount));
            messageService.save(message);
        }
    }


}
