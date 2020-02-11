package com.shengsu.any.app.constant;

/**
 * 防重类型
 * @author zxh
 *
 */
public enum ResultCode {
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败"),
    EXCEPTION(1002, "异常"),
    EXCEPTION_RPC(1002, "rpc异常"),
    EXCEPTION_PARAM_PARSE_FAIL(1002, "参数解析失败"),
    EXCEPTION_PARAM_VALID_FAIL(1002, "参数校验失败"),
    EXCEPTION_PARAM_NULL(1002, "参数为空"),
    //线索录入
    EXCEPTION_CLUE_NOT_FOUND(1002,"无线索"),
    EXCEPTION_CLUE_PRICE(1002,"线索价格必须大于0"),
    EXCEPTION_CLUE_STATE(1002,"当前状态不可编辑"),
    EXCEPTION_CLUE_STATE_ONSHELF(1002,"线索已上架，不可重复上架"),
    EXCEPTION_CLUE_STATE_SOLD(1002,"线索已出售，不可上架"),
    EXCEPTION_CLUE_STATE_OFFSHELF(1002,"线索已下架，不可重复下架"),
    EXCEPTION_CLUE_STATE_FORBID(1002,"当前状态下不允许下架"),
    EXCEPTION_CLUE_STATE_FORBID_DELETE(1002,"当前状态下不允许删除"),
    //登录
    EXCEPTION_LOGIN_USERNAME_PASSWROD_ERROR(1002,"用户名或密码错误"),
    EXCEPTION_LOGIN_PARAM_ERROR(1002,"参数错误"),
    EXCEPTION_LOGIN_TOKEN_EXPIRED(1003,"授权信息已过期"),
    EXCEPTION_LOGIN_TOKEN_INVALID(1004,"无效的认证"),
    EXCEPTION_WECHAT_LOGIN_UNREGISTERED(1003,"微信用户未注册"),
    EXCEPTION_WECHAT_LOGIN_CODE_IS_NULL(1002,"code为空"),
    EXCEPTION_WECHAT_RESPONSE_ERROR(1002,"微信响应错误"),
    EXCEPTION_REQUEST_ERROR(1002,"请求错误"),
    //注册
    EXCEPTION_REGISTER_TEL_BANDED(1002,"手机号已被绑定"),
    EXCEPTION_REGISTER_USER_NOT_EXISTED(1002,"用户不存在"),
    //获取token
    EXCEPTION_PARAM_ERROR(1002,"参数有误"),
    // 短信验证
    SMS_AUTHENTICATION_CODE_OVERTIME(1002,"短信验证码不存在或已过期"),
    SMS_AUTHENTICATION_CODE_ERROR(1002,"短信验证码错误"),
    //数据校验
    EXCEPTION_DATA_EXISTENCE(1002,"数据已存在"),
    EXCEPTION_NOT_NATA_EXISTENCE(1002,"数据不存在"),
    EXCEPTION_DUPLICATE_ACTION(1002,"重复操作"),
    // 微信公众号
    EXCEPTION_WECHAT_USER_TEL_BANDED(1002,"当前账户已绑定过手机号"),
    // 用户认证状态
    EXCEPTION_USER_AUTH_STATE_UNREVIEW(1002,"未提交或已审核"),
    EXCEPTION_USER_AUTH_STATE_IN_REVIEW(1002,"用户正在认证中，不可再提交认证"),
    EXCEPTION_USER_AUTH_STATE_REVIEW_PASS(1002,"用户已认证通过，不可再提交认证");



    private Integer code;
    private String  resultMessage;




    ResultCode(Integer code, String resultMessage) {
        this.code = code;
        this.resultMessage = resultMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public static ResultCode getByCode(Integer code){
        for(ResultCode resultCode:ResultCode.values()){
            if(resultCode.getCode()==code){
                return resultCode;
            }
        }
        return null;
    }
}
