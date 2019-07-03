package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.contant.BizConst;
import com.shengsu.lawcase.entity.*;
import com.shengsu.lawcase.mapper.LawcaseMapper;
import com.shengsu.lawcase.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxh on 2019/5/8.
 */
public class LawcaseServiceImpl extends BaseServiceImpl<Lawcase,String> implements LawcaseService,BizConst{
    @Autowired
    LawcaseMapper lawcaseMapper;

    @Override
    public BaseMapper<Lawcase, String> getBaseMapper() {
        return lawcaseMapper;
    }

    @Autowired
    private LawcasePersonService lawcasePersonService;
    @Autowired
    private LawcaseUserService lawcaseUserService;
    @Autowired
    private LawcaseTaskPersonService lawcaseTaskPersonService;
    @Autowired
    private LawcasePhaseTaskService lawcasePhaseTaskService;
    @Autowired
    private LawcasePhaseService lawcasePhaseService;



    @Override
    public List<Lawcase> getInvestLawcaseList(Lawcase lawcase) {
        return lawcaseMapper.getInvestLawcaseList(lawcase);
    }

    @Override
    public List<Lawcase> getManyByCaseIds(List<String> caseIds) {
        List<Lawcase> lawcases = getMany(caseIds);
        //组装案件人员
        assembleLawcasePerson(lawcases);
        //组装负责人
        assembleResponsiblePerson(lawcases);
        return lawcases;
    }

    @Override
    public List<Lawcase> getPhaseTaskByCaseIds(List<String> caseIds) {
        List<Lawcase> lawcases = getMany(caseIds);
        //组装案件阶段和案件任务
        //assemblePhaseAndTask(lawcases);
        //组装任务
        assembleTask(lawcases);
        return lawcases;
    }

    private void assembleTask(List<Lawcase> lawcases) {
        if(lawcases==null || lawcases.size()==0)
            return;
        List<String> caseIds = new ArrayList<String>();
        for(Lawcase lawcase:lawcases){
            caseIds.add(lawcase.getCaseId());
        }
        //获取执行人
        List<LawcaseTaskPerson> lawcaseTaskPersons = lawcaseTaskPersonService.getManyByCaseIds(caseIds);
        //获取任务
        List<LawcasePhaseTask> lawcasePhaseTasks = lawcasePhaseTaskService.getManyByCaseIds(caseIds);
        //组装执行人员到任务下
        List<LawcasePhaseTask> lawcasePhaseTaskList = assembleExecutor(lawcasePhaseTasks,lawcaseTaskPersons);
        //获取阶段
        List<LawcasePhase> lawcasePhases = lawcasePhaseService.getManyByCaseIds(caseIds);
        //筛选掉案件准备阶段下的任务
        List<LawcasePhaseTask> phaseTasks= assembleTasks(lawcasePhases,lawcasePhaseTaskList);
        for (Lawcase  lawcase :lawcases){
            for (LawcasePhaseTask lawcasePhaseTask:phaseTasks){
                if (lawcase.getCaseId().equals(lawcasePhaseTask.getCaseId())){
                    String totalHours = assembleTotalHours(lawcasePhaseTask.getTaskTime());
                    lawcasePhaseTask.setTaskHours(totalHours);
                    lawcase.getLawcasePhaseTasks().add(lawcasePhaseTask);
                }
            }
        }

    }

    private List<LawcasePhaseTask> assembleTasks(List<LawcasePhase> lawcasePhases, List<LawcasePhaseTask> lawcasePhaseTaskList) {
        if (lawcasePhases == null) {
            return null;
        }
        if (lawcasePhaseTaskList == null) {
            return null;
        }
        List<LawcasePhaseTask> result = new ArrayList<>();
        for (LawcasePhaseTask lawcasePhaseTask : lawcasePhaseTaskList) {
            for (LawcasePhase lawcasePhase : lawcasePhases) {
                if (lawcasePhase.getPhaseId().equals(lawcasePhaseTask.getPhaseId()) && IS_CUSTONMER_VISIBLE == lawcasePhase.getIsCustomerVisible()) {
                    result.add(lawcasePhaseTask);
                }
            }
        }
        return result;
    }

    /**
     * 组装阶段和任务
     * @param lawcases
     */
    private void assemblePhaseAndTask(List<Lawcase> lawcases) {
        if(lawcases==null || lawcases.size()==0)
            return;
        List<String> caseIds = new ArrayList<String>();
        for(Lawcase lawcase:lawcases){
            caseIds.add(lawcase.getCaseId());
        }
        //获取执行人
        List<LawcaseTaskPerson> lawcaseTaskPersons = lawcaseTaskPersonService.getManyByCaseIds(caseIds);
        //获取任务
        List<LawcasePhaseTask> lawcasePhaseTasks = lawcasePhaseTaskService.getManyByCaseIds(caseIds);
        //组装执行人员到任务下
        List<LawcasePhaseTask> lawcasePhaseTaskList = assembleExecutor(lawcasePhaseTasks,lawcaseTaskPersons);
        //获取阶段
        List<LawcasePhase> lawcasePhases = lawcasePhaseService.getManyByCaseIds(caseIds);
        //组装案件任务到阶段下
        List<LawcasePhase> lawcasePhasesList = assembleTask(lawcasePhases,lawcasePhaseTaskList);
        //组装案件阶段到案件下
        assemblePhase(lawcases,lawcasePhasesList);
    }
    /**
     * 收集阶段
     * @param lawcases
     * @param lawcasePhasesList
     */
    private void assemblePhase(List<Lawcase> lawcases,List<LawcasePhase> lawcasePhasesList) {
        if (lawcases == null) {
            return;
        }
        if (lawcasePhasesList == null) {
            return;
        }
        for (LawcasePhase lawcasePhase : lawcasePhasesList) {
            for (Lawcase lawcase : lawcases) {
                if (lawcase.getCaseId().equals(lawcasePhase.getCaseId())) {
                    lawcase.getLawcasePhases().add(lawcasePhase);
                }
            }

        }
    }

    /**
     * 收集任务
     * @param lawcasePhases
     * @param lawcasePhaseTaskList
     * @return
     */
    private List<LawcasePhase> assembleTask(List<LawcasePhase> lawcasePhases,List<LawcasePhaseTask> lawcasePhaseTaskList) {
        if (lawcasePhases == null) {
            return lawcasePhases;
        }
        if (lawcasePhaseTaskList == null) {
            return null;
        }
        for (LawcasePhaseTask lawcasePhaseTask : lawcasePhaseTaskList) {
            for (LawcasePhase lawcasePhase : lawcasePhases) {
                if (lawcasePhase.getPhaseId().equals(lawcasePhaseTask.getPhaseId())) {
                    String totalHours = assembleTotalHours(lawcasePhaseTask.getTaskTime());
                    lawcasePhaseTask.setTaskHours(totalHours);
                    lawcasePhase.getLawcasePhaseTasks().add(lawcasePhaseTask);
                }
            }
        }
        return lawcasePhases;
    }
    /**
     * 收集执行者和附件
     * @param lawcasePhaseTasks
     * @param lawcaseTaskPersons
     * @return
     */
    private List<LawcasePhaseTask> assembleExecutor(List<LawcasePhaseTask> lawcasePhaseTasks,List<LawcaseTaskPerson> lawcaseTaskPersons){
        if (lawcaseTaskPersons == null) {
            return lawcasePhaseTasks;
        }
        if (lawcasePhaseTasks == null) {
            return null;
        }
        for (LawcasePhaseTask lawcasePhaseTask : lawcasePhaseTasks) {
            for (LawcaseTaskPerson lawcaseTaskPerson : lawcaseTaskPersons) {
                if (lawcaseTaskPerson.getTaskId().equals(lawcasePhaseTask.getTaskId())) {
                    if (TASK_PERSON_TYPE_EXECUTOR.equals(lawcaseTaskPerson.getUserType())) {
                        //添加执行者
                        LawcaseUser lawcaseUser = lawcaseUserService.get(lawcaseTaskPerson.getUserId());
                        lawcasePhaseTask.getExecutorUser().add(lawcaseUser);
                    }
                }
            }
        }
        return lawcasePhaseTasks;
    }
    /**
     *  组装案件负责人
     * @param lawcases
     */
    private void assembleResponsiblePerson(List<Lawcase> lawcases){
        if(lawcases==null || lawcases.size()==0)
            return;
        List<String> userIds = new ArrayList<String>();
        for(Lawcase lawcase:lawcases){
            userIds.add(lawcase.getResponsibleUserId());
        }
        List<LawcaseUser> lawcaseUsers = lawcaseUserService.getMany(userIds);
        for(Lawcase lawcase:lawcases){
            for(LawcaseUser lawcaseUser:lawcaseUsers){
                if(lawcase.getResponsibleUserId().equals(lawcaseUser.getUserId()))
                    lawcase.setResponsiblePerson(lawcaseUser);
            }
        }
    }

    /**
     *  组装案件人员（多个）
     * @param lawcases
     */
    private void assembleLawcasePerson(List<Lawcase> lawcases){
        if(lawcases==null || lawcases.size()==0)
            return;
        List<String> caseIds = new ArrayList<String>();
        for(Lawcase lawcase:lawcases){
            caseIds.add(lawcase.getCaseId());
        }
        List<LawcasePerson> lawcasePersons = lawcasePersonService.getManyByCaseIds(caseIds);
        for(LawcasePerson lawcasePerson:lawcasePersons){
            for(Lawcase lawcase:lawcases){
                if(lawcasePerson.getCaseId().equals(lawcase.getCaseId())){
                    switch (lawcasePerson.getPersonType()) {
                        case PERSON_TYPE_LITIGANT:
                            lawcase.getLitigants().add(lawcasePerson);
                            break;
                        case PERSON_TYPE_HEARER:
                            lawcase.getHearers().add(lawcasePerson);
                            break;
                        case PERSON_TYPE_ASSIST:
                            lawcase.getAssistPersons().add(lawcasePerson);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    /**
     * 计算工时
     * @param minute
     * @return
     */
    public String assembleTotalHours(long minute){
        String val=minute +"";
        if("".equals(val))
            return "0";
        double hours = (double)minute/60;
        BigDecimal totalHours = new BigDecimal(hours);
        totalHours = totalHours.setScale(1,BigDecimal.ROUND_HALF_UP);
        return totalHours.toString();
    }
}
