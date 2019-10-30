package com.shengsu.util;

/**
 * 防重类型
 * @author zxh
 *
 */
public enum ResultCode {
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败"),
    EXCEPTION(1002, "异常"),
    
    //登录
    EXCEPTION_LOGIN_PARAM_ERROR(1002,"参数错误"),
    EXCEPTION_LOGIN_USERNAME_EMPTY(1002,"用户名为空"),
    EXCEPTION_LOGIN_USERNAME_NOT_EXIST(1002,"用户名不存在"),
    EXCEPTION_LOGIN_PASSWORD_EMPTY(1002,"密码为空"),
    EXCEPTION_LOGIN_USERNAME_PASSWROD_ERROR(1002,"用户名或密码错误"),
    
    //注册
    EXCEPTION_REGISTER_USER_EXISTED(1002,"用户已存在"),
    
    //用户
    EXCEPTION_USER_PASSWROD_DISAFFINITY(1002,"旧密码不正确"),
    
    //文件上传
    EXCEPTION_IMAGE_UPLOAD(1002, "图片格式不正确,请上传jpg,gif,png,ico,bmp,jpeg格式"),
    
    //案件
    EXCEPTION_LAWCASE_NOT_EXIST(1002,"该案件已被删除,请刷新页面"),
    EXCEPTION_LAWCASE_IS_FILED(1002,"该案件已被归档,请刷新页面"),
    EXCEPTION_LAWCASE_IS_UPDATE(1002,"该案件记录已变更,请刷新页面"),
    EXCEPTION_LAWCASE_IS_NOT_EDIT(1002,"审批中的案件不允许进行编辑"),
    EXCEPTION_RESPONSIBLE_USER_IS_UPDATE(1002,"负责人已变更,请刷新页面"),
    EXCEPTION_NOT_IN_GROUP(1002,"抱歉,您已经不在该案件组中了!"),
    EXCEPTION_IS_RESPONSIBLE_NOT_QUIT(1002,"抱歉,您是负责人不能直接退出!"),
    EXCEPTION_IS_NULL_CURRENTNODE(1002,"当前页面信息已变更，请刷新页面"),
    EXCEPTION_IS_APPROVED(1002,"已审批"),
    EXCEPTION_IS_NOT_CURRENTNODE(1002,"非当前节点"),
    EXCEPTION_NOT_ADD_SAME_PHASE_NAME(1002,"不可添加相同的阶段"),
    EXCEPTION_DATE_PARSE(1002,"日期转换异常"),
    EXCEPTION_TASK_NOT_EXIST(1002,"该任务已被删除,请刷新页面"),
    EXCEPTION_TASK_IS_UPDATE(1002,"该任务记录已变更,请刷新页面"),
    EXCEPTION_CREATE_LAWCASE_EXISTED(1002,"案件已存在"),
    EXCEPTION_LAWCASE_IS_FILED_OPRATION_CHECK(1002,"案件已归档，不可进行此操作"),
    DATA_NOT(1002,"数据不存在"),;
    ;
    
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
		for(ResultCode resultCode: ResultCode.values()){
			if(resultCode.getCode()==code){
				return resultCode;
			}
		}
		return null;
	}
}
