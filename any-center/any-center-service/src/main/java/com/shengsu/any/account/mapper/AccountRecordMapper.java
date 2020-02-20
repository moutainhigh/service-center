package com.shengsu.any.account.mapper;

import com.shengsu.any.account.entity.AccountRecord;
import com.shengsu.any.account.po.TotalExpendPo;
import com.shengsu.any.account.po.TotalIncomePo;
import com.shengsu.any.account.vo.AccounListByPageVo;
import com.shengsu.any.account.vo.AccountDetailListByPageVo;
import com.shengsu.any.account.vo.RichesListByPageVo;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:23
 **/
@Mapper
public interface AccountRecordMapper extends BaseMapper<AccountRecord,String> {
    List<AccountRecord> listIncome(String userId);

    List<AccountRecord> listExpend(String userId);
    int countAll(AccountDetailListByPageVo accountDetailListByPageVo);
    List<AccountRecord> listByPage(AccountDetailListByPageVo accountDetailListByPageVo);

    List<TotalIncomePo> totalIncome(AccounListByPageVo accounListByPageVo);

    List<TotalExpendPo> totalExpend(AccounListByPageVo accounListByPageVo);

    List<AccountRecord> getManyByUserId(String userId);

    List<TotalIncomePo> historyRecharge(RichesListByPageVo richesListByPageVo);
}
