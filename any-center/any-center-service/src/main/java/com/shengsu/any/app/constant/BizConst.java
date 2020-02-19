package com.shengsu.any.app.constant;


/**
 * 
 * @ClassName: BizConst
 * @Description: 业务常量
 * @author zxh
 * 
 */
public interface BizConst {
    //编码前缀-线索
    String PREFIX_CLUE_CODE = "XS";
    //线索状态-待上架
    String CLUE_STATE_PEND = "pend";
    //线索状态-已上架
    String CLUE_STATE_ONSHELF = "onShelf";
    //线索状态-已下架
    String CLUE_STATE_OFFSHELF = "offShelf";
    //线索状态-已出售
    String CLUE_STATE_SOLD = "sold";
    //字典-用户状态code
    String DICT_CODE_AUTH_STATE= "auth_state";
    //字典-案件类型(领域)code
    String DICT_CODE_FIELD= "field";
    //用户状态-未认证
    String USER_AUTH_STATE_UNAUTHORIZED = "0";
    //用户状态-认证中
    String USER_AUTH_STATE_AUTHENTICATION = "1";
    //用户状态-已认证
    String USER_AUTH_STATE_AUTHENTICATED = "2";
    //用户状态-已拒绝
    String USER_AUTH_STATE_REJECTED = "3";
    //账户余额来源-账户余额-充值
    String ACCOUNT_BALANCE_SOURCE_RECHANGE = "other";
    //账户余额来源-账户余额-提现
    String ACCOUNT_BALANCE_SOURCE_CASH_OUT = "account_balance";
    //账户余额来源-账户余额
    String ACCOUNT_BALANCE_SOURCE = "account_balance";
    //账户操作类型-提现
    String ACCOUNT_ACTION_TYPE_RECHARGE = "recharge";
    //账户操作类型-提现
    String ACCOUNT_ACTION_TYPE_CASH_OUT = "cash_out";
}
