package com.shengsu.lawcase.service.impl;

import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.lawcase.entity.Lawcase;
import com.shengsu.lawcase.entity.LawcasePerson;
import com.shengsu.lawcase.entity.LawcaseUser;
import com.shengsu.lawcase.mapper.LawcaseMapper;
import com.shengsu.lawcase.service.LawcasePersonService;
import com.shengsu.lawcase.service.LawcaseService;
import com.shengsu.lawcase.service.LawcaseUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.shengsu.app.constant.BizConst.PERSON_TYPE_LITIGANT;

/**
 * Created by zxh on 2019/5/8.
 */
public class LawcaseServiceImpl extends BaseServiceImpl<Lawcase,String> implements LawcaseService{
    LawcaseMapper lawcaseMapper;
    @Autowired
    private LawcasePersonService lawcasePersonService;
    @Autowired
    private LawcaseUserService lawcaseUserService;
    @Autowired
    public void setLawcaseMapper(LawcaseMapper lawcaseMapper){
        this.lawcaseMapper = lawcaseMapper;
        this.baseMapper = lawcaseMapper;
    }

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
                        default:
                            break;
                    }
                }
            }
        }
    }
}
