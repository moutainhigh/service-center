package com.shengsu.any.app.constant;


/**
 * 
 * @ClassName: BizConst
 * @Description: 业务常量
 * @author zxh
 * 
 */
public interface BizConst {
    //字典用户状态code
    String DICT_CODE_AUTH_STATE= "auth_state";
    //用户状态-未认证
    String USER_AUTH_STATE_UNAUTHORIZED = "0";
    //用户状态-认证中
    String USER_AUTH_STATE_AUTHENTICATION = "1";
    //用户状态-已认证
    String USER_AUTH_STATE_AUTHENTICATED = "2";
    //用户状态-已拒绝
    String USER_AUTH_STATE_REJECTED = "3";
}
