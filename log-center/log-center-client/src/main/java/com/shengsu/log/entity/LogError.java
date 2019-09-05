package com.shengsu.log.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: LogError
 * @Description: (错误日志)
 * @author zxh
 * @date 2018-9-10
 *
 */
@Data
public class LogError extends BaseEntity
{

    /**
     * @Fields serialVersionUID : (序列号)
     */
    private static final long serialVersionUID = 6032198921602179162L;

    private String id;
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

    /**
     * 参数
     */
    private String errParam;

}
