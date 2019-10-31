package com.shengsu.helper.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 消息实体类
 * @author zxh
 *
 */

@Data
public class Message extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String messageId;
	private String userId;
	private String messageContent;
	private String messageType;
	private String readState;
	private String extrasparam;
	private String messageTitle;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date pushTime;//提醒时间
	private String scheduleId;
}
