package com.shengsu.lawcase.entity;

import java.util.ArrayList;
import java.util.List;

import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * 案件阶段
 * @author zxh
 *
 */
@Data
public class LawcasePhase extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String phaseId; 
	private String caseId; 
	private String phaseName;
    private String delFlag;
    private int sort;
    private Short isCustomerVisible;//是否可见
    private List<LawcasePhaseTask> lawcasePhaseTasks = new ArrayList<LawcasePhaseTask>();//阶段任务
}
