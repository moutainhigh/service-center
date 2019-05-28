package com.shengsu.lawcase.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.lawcase.entity.LawcasePhaseTask;

import java.util.List;

/**
 * Created by lipiao on 2019/5/21.
 */
public interface LawcasePhaseTaskService extends BaseService<LawcasePhaseTask,String> {
    List<LawcasePhaseTask> getManyByCaseIds(List<String> caseIds);
}
