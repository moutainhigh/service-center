package com.shengsu.lawcase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import lombok.Data;

/**
 * 案件阶段任务
 * @author zxh
 *
 */
@Data
public class LawcasePhaseTask extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String taskId;
	private String phaseId; 
	private String caseId; 
	private String taskName;
    private String taskContent;
    private long taskTime;//工时
    private long totalHours;//总计工时
    private List<LawcaseTaskPerson> executors = new ArrayList<LawcaseTaskPerson>();//执行人
    private List<LawcaseUser> executorUser = new ArrayList<LawcaseUser>();
    private String taskHours;
	private String state;//任务状态
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date startTimeDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date endTimeDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date completeTime;

}
