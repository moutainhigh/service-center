package com.shengsu.log.entity;

import com.shengsu.base.entity.BaseEntity;

import java.util.Date;

/**
 * @ClassName: LogOp
 * @Description: (操作日志)
 * @author zxh
 * @date 2018-9-10
 * 
 */
public class LogOp extends BaseEntity
{
    /**
     * @ClassName: LogType
     * @Description: (操作类型)
     * 
     */
    public enum OpType
    {
        INSERT, UPDATE, DELETE, QUERY, OTHER
    }

    /**
     * @Fields serialVersionUID : (序列号)
     */
    private static final long serialVersionUID = -6350094733983342070L;
    private String id;
    /**
     * 操作类型
     */
    private OpType opType;

    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 操作员IP
     */
    private String opIp;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 结果状态：0-成功 1-失败
     */
    private int state;

    /**
     * 日志内容
     */
    private String content;

    public OpType getOpType()
    {
        return opType;
    }

    /**
     * @param opType 要设置的 opType
     */
    public void setOpType(OpType opType)
    {
        this.opType = opType;
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

    /**
     * @return opIp
     */
    public String getOpIp()
    {
        return opIp;
    }

    /**
     * @param opIp 要设置的 opIp
     */
    public void setOpIp(String opIp)
    {
        this.opIp = opIp;
    }

    /**
     * @return opTime
     */
    public Date getOpTime()
    {
        return opTime;
    }

    /**
     * @param opTime 要设置的 opTime
     */
    public void setOpTime(Date opTime)
    {
        this.opTime = opTime;
    }

    /**
     * @return state
     */
    public int getState()
    {
        return state;
    }

    /**
     * @param state 要设置的 state
     */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
     * @return content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content 要设置的 content
     */
    public void setContent(String content)
    {
        this.content = content;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
