package com.shengsu.helper.entity;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * @author zxh
 * @ClassName: LawcaseDictionary
 * @Description: 系统字典（实体类）
 * @date 2018-7-6
 */
@Data
public class SystemDictionary extends BaseEntity {
    private static final long serialVersionUID = -1962148635528521842L;
    /**
     * 字典id
     */
    private String dictId;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 状态值
     */
    private String displayValue;
    /**
     * 状态名称
     */
    private String displayName;

    /**
     * 创建人
     */
    private String creator;
    /**
     * 不作废 or 作废
     */
    private String delFlag;
    /**
     * 系统标记
     */
    private String systemTag;
}
