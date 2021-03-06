package com.shengsu.log.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zxh
 * @ClassName: LogBusiness
 * @Description: (业务日志)
 * @date 2019-5-24
 */
@Data
@ToString
public class LogBusiness extends BaseEntity {
    /**
     * @Fields serialVersionUID : (序列号)
     */
    private static final long serialVersionUID = -6350094733983342070L;
    private Integer id;
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

    /**
     * 系统标签
     */
    private String systemTag;
    /**
     * 请求ip
     */
    private String requestIp;
    private String startTime;//创建开始时间
    private String endTime;//创建结束时间
}
