package com.shengsu.lawcase.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.lawcase.entity.Lawcase;
import com.shengsu.lawcase.entity.LawcasePerson;

import java.util.List;

/**
 * Created by lipiao on 2019/5/21.
 */
public interface LawcasePersonService extends BaseService<LawcasePerson,String>{
    List<LawcasePerson> getManyByCaseIds(List<String> caseIds);
}
