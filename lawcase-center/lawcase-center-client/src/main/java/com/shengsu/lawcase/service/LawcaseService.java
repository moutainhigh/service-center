package com.shengsu.lawcase.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.lawcase.entity.DailyLawcaseStatisticsVo;
import com.shengsu.lawcase.entity.Lawcase;

import java.util.List;

/**
 * Created by zxh on 2019/5/8.
 */
public interface LawcaseService extends BaseService<Lawcase,String>{
    List<Lawcase> getInvestLawcaseList(Lawcase lawcase);
    List<Lawcase> getManyByCaseIds(List<String> caseIds);
    List<Lawcase> getPhaseTaskByCaseIds(List<String> caseIds);
    List<DailyLawcaseStatisticsVo> countLawcaseList(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);
}
