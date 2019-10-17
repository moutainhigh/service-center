package com.shengsu.lawcase.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.contant.BizConst;
import com.shengsu.lawcase.entity.*;
import com.shengsu.lawcase.mapper.LawcaseMapper;
import com.shengsu.lawcase.service.*;
import com.shengsu.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zxh on 2019/5/8.
 */
@Service("lawcaseService")
public class LawcaseServiceImpl extends BaseServiceImpl<Lawcase,String> implements LawcaseService,BizConst {
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
    @Override
    public List<DailyLawcaseStatisticsVo> countLawcaseList(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo) {
        String startTime = DateUtil.getDate(dailyLawcaseStatisticsVo.getStartDate());
        String endTime = DateUtil.getDate(dailyLawcaseStatisticsVo.getEndDate());
        List<String> dates = new ArrayList<>();
        try {
            dates = DateUtil.getDate(startTime,endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<DailyLawcaseStatisticsVo> result = new ArrayList<>();
        //查询案件数据
        List<DailyLawcaseStatisticsVo> lawcaseVos = lawcaseMapper.countLawcaseList(dailyLawcaseStatisticsVo);
        //查询日期和代理标的
        List<DailyLawcaseStatisticsVo> agencyTargetVos =  lawcaseMapper.getAgencyTarget(dailyLawcaseStatisticsVo);
        //查询审批状态当前日期的数据
        List<DailyLawcaseStatisticsVo> approveStatusCurrentDateVos = lawcaseMapper.getApproveStatusCurrentDate(dailyLawcaseStatisticsVo);
        //查询审批状态去年的数据
        List<DailyLawcaseStatisticsVo> approveStatusLastYearVos = lawcaseMapper.getApproveStatusLastYear(dailyLawcaseStatisticsVo);
        //查询审批状态上周的数据
        List<DailyLawcaseStatisticsVo> approveStatusLastWeekVos = lawcaseMapper.getApproveStatusLastWeek(dailyLawcaseStatisticsVo);

        //查询标的当前日期的数据
        List<DailyLawcaseStatisticsVo> targetCurrentDateVos = lawcaseMapper.getTargetCurrentDate(dailyLawcaseStatisticsVo);
        //查询标的去年的数据
        List<DailyLawcaseStatisticsVo> targetLastYearVos = lawcaseMapper.getTargetLastYear(dailyLawcaseStatisticsVo);
        //查询标的上周的数据
        List<DailyLawcaseStatisticsVo> targetLastWeekVos = lawcaseMapper.getTargetLastWeek(dailyLawcaseStatisticsVo);

        for (int i = 0; i < dates.size(); i++) {
            DailyLawcaseStatisticsVo resultVo = new DailyLawcaseStatisticsVo();
            resultVo.setCountDate(dates.get(i));
            result.add(resultVo);
        }
        //获取时间段中的所有数据
        for (DailyLawcaseStatisticsVo resultVo : result) {
            for (DailyLawcaseStatisticsVo caseVo : lawcaseVos) {
                if (resultVo.getCountDate().equals(caseVo.getCountDate())) {
                    makeResult(resultVo,caseVo);
                }
            }
            for (DailyLawcaseStatisticsVo agencyTargetVo : agencyTargetVos) {
                if (resultVo.getCountDate().equals(agencyTargetVo.getCountDate())) {
                    resultVo.setCountAgencyTarget(agencyTargetVo.getCountAgencyTarget());
                }
            }
            //设置案件审批同比和环比
            makeApproveStatus(resultVo,approveStatusCurrentDateVos,approveStatusLastYearVos,approveStatusLastWeekVos);
            //设置标的同比环比
            makeTarget(resultVo,targetCurrentDateVos,targetLastYearVos,targetLastWeekVos);
        }
        return result;
    }
    private void makeTarget(DailyLawcaseStatisticsVo lawcaseVo,
                            List<DailyLawcaseStatisticsVo> targetCurrentDateVos,
                            List<DailyLawcaseStatisticsVo> targetLastYearVos, List<DailyLawcaseStatisticsVo> targetLastWeekVos) {
        BigDecimal countTargetCurrentDate = new BigDecimal(0);
        String countDate = lawcaseVo.getCountDate();
        for (DailyLawcaseStatisticsVo currentDateVo :targetCurrentDateVos) {
            if (countDate.equals(currentDateVo.getCountDate())) {
                countTargetCurrentDate = currentDateVo.getCountTargetCurrentDate();
                break;
            }
        }
        Date lastWeek = null;
        Date lastYear = null;
        try {
            lastWeek = DateUtil.addDays(DateUtil.parseDate(countDate),-7);
            lastYear = DateUtil.addYears(DateUtil.parseDate(countDate),-1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String lastWeekDate = DateUtil.getDate(lastWeek);
        String lastYearDate = DateUtil.getDate(lastYear);
        BigDecimal countTargetLastWeek = new BigDecimal(0);
        BigDecimal countTargetLastYear = new BigDecimal(0);
        for (DailyLawcaseStatisticsVo lastWeekVo :targetLastWeekVos) {
            if (lastWeekDate.equals(lastWeekVo.getCountDate())) {
                countTargetLastWeek = lastWeekVo.getCountTargetLastWeek();
                break;
            }
        }
        for (DailyLawcaseStatisticsVo lastYearVo :targetLastYearVos) {
            if (lastYearDate.equals(lastYearVo.getCountDate())){
                countTargetLastYear = lastYearVo.getCountTargetLastYear();
                break;
            }
        }
        String chainRatio = "";
        String yearOnYear = "";
        if (new BigDecimal(0).equals(countTargetLastWeek)) {
            chainRatio = "0";
        }else {
            BigDecimal countWeekBigDecimal = countTargetCurrentDate.subtract(countTargetLastWeek);
            if (countWeekBigDecimal.compareTo(new BigDecimal(0))<0) {
                chainRatio= "-"+countTargetLastWeek.subtract(countTargetCurrentDate).divide(countTargetLastWeek,4,BigDecimal.ROUND_HALF_UP).toString();
            }else {
                chainRatio= countTargetCurrentDate.subtract(countTargetLastWeek).divide(countTargetLastWeek,4,BigDecimal.ROUND_HALF_UP).toString();
            }
        }
        if (new BigDecimal(0).equals(countTargetLastYear)) {
            yearOnYear = "0";
        }else {
            BigDecimal countYearBigDecimal = countTargetCurrentDate.subtract(countTargetLastYear);
            if (countYearBigDecimal.compareTo(new BigDecimal(0))<0) {
                yearOnYear= "-"+countTargetLastYear.subtract(countTargetCurrentDate).divide(countTargetLastYear,4,BigDecimal.ROUND_HALF_UP).toString();
            }else {
                yearOnYear= countTargetCurrentDate.subtract(countTargetLastYear).divide(countTargetLastYear,4,BigDecimal.ROUND_HALF_UP).toString();
            }
        }
        lawcaseVo.setCountTargetYearOnYear(yearOnYear);
        lawcaseVo.setCountTargetChainRatio(chainRatio);

    }
    private void makeApproveStatus(DailyLawcaseStatisticsVo lawcaseVo,List<DailyLawcaseStatisticsVo> approveStatusCurrentDateVos, List<DailyLawcaseStatisticsVo> approveStatusLastYearVos, List<DailyLawcaseStatisticsVo> approveStatusLastWeekVos) {
        BigDecimal countApproveStatusCurrentDate = new BigDecimal(0);
        String countDate = lawcaseVo.getCountDate();
        for (DailyLawcaseStatisticsVo currentDateVo :approveStatusCurrentDateVos) {
            if (countDate.equals(currentDateVo.getCountDate())) {
                countApproveStatusCurrentDate = currentDateVo.getCountApproveStatusCurrentDate();
                break;
            }
        }
        Date lastWeek = null;
        Date lastYear = null;
        try {
            lastWeek = DateUtil.addDays(DateUtil.parseDate(countDate),-7);
            lastYear = DateUtil.addYears(DateUtil.parseDate(countDate),-1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String lastWeekDate = DateUtil.getDate(lastWeek);
        String lastYearDate = DateUtil.getDate(lastYear);
        BigDecimal countApproveStatusLastWeek = new BigDecimal(0);
        BigDecimal countApproveStatusLastYear = new BigDecimal(0);
        for (DailyLawcaseStatisticsVo lastWeekVo :approveStatusLastWeekVos) {
            if (lastWeekDate.equals(lastWeekVo.getCountDate())) {
                countApproveStatusLastWeek = lastWeekVo.getCountApproveStatusLastWeek();
                break;
            }
        }
        for (DailyLawcaseStatisticsVo lastYearVo :approveStatusLastYearVos) {
            if (lastYearDate.equals(lastYearVo.getCountDate())){
                countApproveStatusLastYear = lastYearVo.getCountApproveStatusLastYear();
                break;
            }
        }
        String chainRatio = "";
        String yearOnYear = "";
        if (new BigDecimal(0).equals(countApproveStatusLastWeek)) {
            chainRatio = "0";
        }else {
            BigDecimal countWeekBigDecimal = countApproveStatusCurrentDate.subtract(countApproveStatusLastWeek);
            if (countWeekBigDecimal.compareTo(new BigDecimal(0))<0) {
                chainRatio= "-"+countApproveStatusLastWeek.subtract(countApproveStatusCurrentDate).divide(countApproveStatusLastWeek,4,BigDecimal.ROUND_HALF_UP).toString();
            }else {
                chainRatio= countApproveStatusCurrentDate.subtract(countApproveStatusLastWeek).divide(countApproveStatusLastWeek,4,BigDecimal.ROUND_HALF_UP).toString();
            }
        }
        if (new BigDecimal(0).equals(countApproveStatusLastYear)) {
            yearOnYear = "0";
        }else {
            BigDecimal countYearBigDecimal = countApproveStatusCurrentDate.subtract(countApproveStatusLastYear);
            if (countYearBigDecimal.compareTo(new BigDecimal(0))<0) {
                yearOnYear= "-"+countApproveStatusLastYear.subtract(countApproveStatusCurrentDate).divide(countApproveStatusLastYear,4,BigDecimal.ROUND_HALF_UP).toString();
            }else {
                yearOnYear= countApproveStatusCurrentDate.subtract(countApproveStatusLastYear).divide(countApproveStatusLastYear,4,BigDecimal.ROUND_HALF_UP).toString();
            }
        }
        lawcaseVo.setCountApproveStatusYearOnYear(yearOnYear);
        lawcaseVo.setCountApproveStatusChainRatio(chainRatio);
    }
    private void makeResult(DailyLawcaseStatisticsVo result, DailyLawcaseStatisticsVo caseVo) {
        result.setCountDate(caseVo.getCountDate());
        result.setCountApprovingStatus(caseVo.getCountApprovingStatus());
        result.setCountApproveStatusPass(caseVo.getCountApproveStatusPass());
        result.setCountApproveStatusAgent(caseVo.getCountApproveStatusAgent());
        result.setCountApproveStatusReject(caseVo.getCountApproveStatusReject());
        result.setCountCivilAndCommercialLitigation(caseVo.getCountCivilAndCommercialLitigation());
        result.setCountCriminalProceeding(caseVo.getCountCriminalProceeding());
        result.setCountForeignLitigation(caseVo.getCountForeignLitigation());
        result.setCountAdministrativeCase(caseVo.getCountAdministrativeCase());
        result.setCountContractDispute(caseVo.getCountContractDispute());
        result.setCountOther(caseVo.getCountOther());
        result.setCountLawyerLetter(caseVo.getCountLawyerLetter());
        result.setCountLegalCounsel(caseVo.getCountLegalCounsel());
        result.setCountSpecialLegalService(caseVo.getCountSpecialLegalService());
        result.setCountNonLitigationCase(caseVo.getCountNonLitigationCase());
        result.setCountTargetOfEntry(caseVo.getCountTargetOfEntry());

    }
}
