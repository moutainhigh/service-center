package com.shengsu.contant;


/**
 * 
 * @ClassName: BizConst
 * @Description: 业务常量
 * @author zxh
 * 
 */
public interface BizConst {
	//案件人员类型-当事人
	public static String PERSON_TYPE_LITIGANT = "1";
	//案件人员类型-审理人
	public static String PERSON_TYPE_HEARER = "2";
	//案件人员类型-辅助人
	public static String PERSON_TYPE_ASSIST = "3";
	//任务人员类型-发起者
	public static String TASK_PERSON_TYPE_ORIGINATOR = "1";
	//任务人员类型-执行人
	public static String TASK_PERSON_TYPE_EXECUTOR = "2";
	//客户不可见(阶段)
	public static Short IS_NOT_CUSTONMER_VISIBLE = 0;
	//客户可见(阶段)
	public static Short IS_CUSTONMER_VISIBLE = 1;
}
