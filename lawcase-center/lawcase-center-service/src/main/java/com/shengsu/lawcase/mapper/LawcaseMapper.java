package com.shengsu.lawcase.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.lawcase.entity.DailyLawcaseStatisticsVo;
import com.shengsu.lawcase.entity.Lawcase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zxh on 2019/5/8.
 */

@Mapper
public interface LawcaseMapper extends BaseMapper<Lawcase,String> {
    List<Lawcase> getInvestLawcaseList(Lawcase lawcase);

    List<DailyLawcaseStatisticsVo> countLawcaseList(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getAgencyTarget(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getApproveStatusCurrentDate(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getApproveStatusLastYear(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getApproveStatusLastWeek(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getTargetCurrentDate(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getTargetLastYear(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);

    List<DailyLawcaseStatisticsVo> getTargetLastWeek(DailyLawcaseStatisticsVo dailyLawcaseStatisticsVo);
}
