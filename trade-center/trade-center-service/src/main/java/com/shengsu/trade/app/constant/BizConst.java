package com.shengsu.trade.app.constant;


/**
 * @author zxh
 * @ClassName: BizConst
 * @Description: 业务常量
 */
public interface BizConst {
    String SYSTEM_TAG_SHENGSU= "shengsu";
    String SYSTEM_TAG_YUANSHOU = "yuanshou";
    String SYSTEM_TAG_LVSHIFU = "lvshifu";
    //在线咨询标记
    String CONSULT_TAG_ONLINE= "online";
    //电话咨询标记
    String CONSULT_TAG_TEL = "tel";
    //字典-订单状态
    String DICT_CODE_ORDER_STATUS= "order_status";
    //账户余额来源-账户余额-充值
    String ACCOUNT_BALANCE_SOURCE_RECHANGE = "other";
    //账户余额来源-账户余额-提现
    String ACCOUNT_BALANCE_SOURCE_CASH_OUT = "account_balance";
    //账户余额来源-微信-充值
    String ACCOUNT_BALANCE_SOURCE_WECHAT_RECHANGE = "wechat";
    //账户操作类型-后台充值
    String ACCOUNT_ACTION_TYPE_RECHARGE = "recharge";
    //账户操作类型-H5充值
    String ACCOUNT_ACTION_TYPE_H5_RECHARGE = "H5_recharge";
    //账户操作类型-提现
    String ACCOUNT_ACTION_TYPE_CASH_OUT = "cash_out";
    //账户操作类型-购买线索
    String ACCOUNT_ACTION_TYPE_BUY_CLUE= "buy_clue";

    //支付类型-微信
    String PAY_TYPE_WECHAT = "wechat";
    //支付类型-支付宝
    String PAY_TYPE_ALIPAY = "alipay";
    //支付类型-百度
    String PAY_TYPE_BDPAY = "bdpay";
    // 下单标记-微信公众号
    String ORDER_FLAG_WECHAT_GZH= "AWG";
    // 胜诉平台支付下单标记-微信小程序
    String ORDER_FLAG_SHENGSU_WECHAT_WEAPP= "SWA";
    // 援手平台支付下单标记-微信小程序
    String ORDER_FLAG_YUANSHOU_WECHAT_WEAPP= "YWA";
    // 律师傅支付下单标记-微信小程序
    String ORDER_FLAG_LVSHIFU_WECHAT_WEAPP= "LWA";
    // 胜诉平台下单标记-微信H5
    String ORDER_FLAG_SHENGSU_WECHAT_MWEB= "SWM";
    // 援手平台下单标记-微信H5
    String ORDER_FLAG_YUANSHOU_WECHAT_MWEB= "YWM";

    // 胜诉平台下单标记-支付宝案源王H5
    String ORDER_FLAG_SHENGSU_ALIPAY_ANY_MWEB= "AAT";
    // 胜诉平台下单标记-支付宝市场推广H5
    String ORDER_FLAG_SHENGSU_ALIPAY_MARKET_MWEB= "SAM";
    // 援手平台支付下单标记-支付宝市场推广H5
    String ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_MWEB= "YAM";

    // 胜诉平台下单标记-支付宝市场推广小程序
    String ORDER_FLAG_SHENGSU_ALIPAY_MARKET_APP= "SAA";
    // 援手平台支付下单标记-支付宝市场推广小程序
    String ORDER_FLAG_YUANSHOU_ALIPAY_MARKET_APP="YAA";

    //订单状态-未支付
    String ORDER_STATUS_UNPAID= "0";
    //订单状态-已支付
    String ORDER_STATUS_PAID= "1";
    //订单状态-已关闭
    String ORDER_STATUS_CLOSED= "2";

    //收支类型-收入
    String IN_OR_OUT_TYPE_INCOME = "income";
    //收支类型-支出
    String IN_OR_OUT_TYPE_EXPEND= "expend";

    //支付渠道-微信
    String PAY_SUBTYPE_WECHAT = "wechat";
    //支付渠道-支付宝
    String PAY_SUBTYPE_ALIPAY = "alipay";
    //支付渠道-度小满
    String PAY_SUBTYPE_DUXIAOMAN = "duxiaoman";
    //支付渠道-百度闪付
    String PAY_SUBTYPE_BAIDU_FLASH_PAYMENT = "baidu_flash_payment";
    //支付渠道-花呗
    String PAY_SUBTYPE_HUABEI = "huabei";

    //百度订单状态-已支付
    String BAIDU_ORDER_STATUS_PAID= "2";


}
