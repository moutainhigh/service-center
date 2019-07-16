package com.shengsu.log.entity;

import com.shengsu.base.entity.BaseEntity;

import java.util.Date;

/**
 * @ClassName: LogError
 * @Description: (错误日志)
 * @author zxh
 * @date 2018-9-10
 *
 */
public class LogError extends BaseEntity
{

    /**
     * @Fields serialVersionUID : (序列号)
     */
    private static final long serialVersionUID = 6032198921602179162L;

    private String id;
    /**
     * 发生时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 错误名
     */
    private String errName;

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 错误描述
     */
    private String errRemark;

    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * @param createTime 要设置的 createTime
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return errName
     */
    public String getErrName()
    {
        return errName;
    }

    /**
     * @param errName 要设置的 errName
     */
    public void setErrName(String errName)
    {
        this.errName = errName;
    }

    /**
     * @return errMsg
     */
    public String getErrMsg()
    {
        return errMsg;
    }

    /**
     * @param errMsg 要设置的 errMsg
     */
    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }

    /**
     * @return errRemark
     */
    public String getErrRemark()
    {
        return errRemark;
    }

    /**
     * @param errRemark 要设置的 errRemark
     */
    public void setErrRemark(String errRemark)
    {
        this.errRemark = errRemark;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
