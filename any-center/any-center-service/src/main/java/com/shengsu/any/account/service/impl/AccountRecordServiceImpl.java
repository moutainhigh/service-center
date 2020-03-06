package com.shengsu.any.account.service.impl;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.mapper.AccountRecordMapper;
import com.shengsu.any.account.po.*;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.util.AccountRecordUtils;
import com.shengsu.any.account.vo.*;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.any.clue.util.ClueUtils;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.user.entity.AliasUser;
import com.shengsu.user.service.AliasUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:25
 **/
@Service("accountRecordService")
public class AccountRecordServiceImpl extends BaseServiceImpl<AccountRecord, String> implements AccountRecordService {
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountServcie accountServcie;
    @Autowired
    private AccountRecordMapper accountRecordMapper;
    @Autowired
    private AliasUserService aliasUserService;
    @Override
    public BaseMapper<AccountRecord, String> getBaseMapper() {
        return accountRecordMapper;
    }

    @Override
    public ResultBean listIncome(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        List<AccountRecord> accountRecords = accountRecordMapper.listIncome(user.getUserId());
        List<IncomeListPo> result = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecords){
            IncomeListPo incomeListPo = AccountRecordUtils.toIncomeListPo(accountRecord);
            result.add(incomeListPo);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }

    @Override
    public ResultBean listExpend(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        List<AccountRecord> accountRecords = accountRecordMapper.listExpend(user.getUserId());

        List<String> clueIds = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecords) {
            clueIds.add(accountRecord.getClueId());
        }
        List<Clue> clues = new ArrayList<>();
        if (null != clueIds && clueIds.size()>0){
            clues = clueService.getMany(clueIds);
        }
        Map<String, Clue> clueMap = ClueUtils.toClueMap(clues);
        List<ExpendListPo> result = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecords){
            ExpendListPo expendListPo = AccountRecordUtils.toExpendListPo(accountRecord);
            if (clueMap.get(accountRecord.getClueId()) != null) {
                expendListPo.setClueCode(clueMap.get(accountRecord.getClueId()).getClueCode());
            }
            result.add(expendListPo);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }

    @Override
    public ResultBean listAccountDetailByPage(AccountDetailListByPageVo accountDetailVo) {
        String userId = null;
        if (StringUtils.isNotBlank(accountDetailVo.getTel())){
            userId = userService.getUserIdByTel(accountDetailVo.getTel());
        }
        accountDetailVo.setUserId(userId);
       Map<String, Object> map = new HashMap<>();
       int totalCount = accountRecordMapper.countAll(accountDetailVo);
        if (totalCount > 0) {
            List<AccountRecord> root = accountRecordMapper.listByPage(accountDetailVo);

            List<String> userIds = new ArrayList<>();
            for (AccountRecord accountRecord : root) {
                userIds.add(accountRecord.getUserId());
            }
            List<User> users = new ArrayList<>();
            if (null != userIds && userIds.size()>0){
                users = userService.getMany(userIds);
            }
            Map<String, User> userMap = UserUtils.toUserMap(users);

            List<AccountRecordDetailsPo> accountRecordDetailsPos = AccountRecordUtils.toAccountRecordDetailsPos(root,userMap);
            map.put("root", accountRecordDetailsPos);
            map.put("totalCount", totalCount);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    @Override
    public List<TotalIncomePo> totalIncome(AccounListByPageVo accounListByPageVo) {
        return accountRecordMapper.totalIncome(accounListByPageVo);
    }

    @Override
    public List<TotalExpendPo> totalExpend(AccounListByPageVo accounListByPageVo) {
        return accountRecordMapper.totalExpend(accounListByPageVo);
    }

    @Override
    public void create(BigDecimal beforeBalance, BigDecimal afterBalance,String source,BalanceChangeVo balanceChangeVo) {
        AccountRecord accountRecord=AccountRecordUtils.toAccountRecord(beforeBalance,afterBalance,source,balanceChangeVo);
        save(accountRecord);
    }
    @Override
    public ResultBean getBalanceChangeRecord(BalanceChangeRecordVo balanceChangeRecordVo) {
        String userId = balanceChangeRecordVo.getUserId();
        List<AccountRecord> accountRecords = accountRecordMapper.getManyByUserId(userId);

        List<String> userIds = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecords) {
            userIds.add(accountRecord.getCreator());
        }
        List<AliasUser> aliasUsers= new ArrayList<>();
        if (null != userIds && userIds.size()>0){
            aliasUsers = aliasUserService.getAliasUsers(userIds);
        }
        Map<String, AliasUser> aliasUserMap = UserUtils.toAliasUserMap(aliasUsers);
        List<BalanceChangeRecordPo> balanceChangeRecordPos = AccountRecordUtils.toBalanceChangeRecordPos(accountRecords,aliasUserMap);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, balanceChangeRecordPos);
    }

    @Override
    public List<TotalIncomePo> historyRecharge(RichesListByPageVo richesListByPageVo) {
        return accountRecordMapper.historyRecharge(richesListByPageVo);
    }

}
