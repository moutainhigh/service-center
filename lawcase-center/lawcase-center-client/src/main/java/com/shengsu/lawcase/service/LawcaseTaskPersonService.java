package com.shengsu.lawcase.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.lawcase.entity.LawcasePerson;
import com.shengsu.lawcase.entity.LawcaseTaskPerson;

import java.util.List;

/**
 * Created by lipiao on 2019/5/21.
 */
public interface LawcaseTaskPersonService extends BaseService<LawcaseTaskPerson,String>{
    List<LawcaseTaskPerson> getManyByCaseIds(List<String> caseIds);
}
