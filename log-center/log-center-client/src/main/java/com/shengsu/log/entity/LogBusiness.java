package com.shengsu.log.entity;

import com.shengsu.base.entity.BaseEntity;

import java.util.Date;

/**
 * @ClassName: LogBusiness
 * @Description: (业务日志)
 * @author zxh
 * @date 2019-5-24
 * 
 */
public class LogBusiness extends BaseEntity
{
    /**
     * @Fields serialVersionUID : (序列号)
     */
    private static final long serialVersionUID = -6350094733983342070L;
    private String id;
    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 操作员
     */
    private String operator;
    
    /**
     * 模块
     */
    private String module;

    /**
     * 操作时间
     */
    private Date operateTime;
    
    /**
     * 请求参数
     */
    private String requestArg;
    
    /**
     * 响应消息
     */
    private String responseMsg;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestArg() {
		return requestArg;
	}

	public void setRequestArg(String requestArg) {
		this.requestArg = requestArg;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}
