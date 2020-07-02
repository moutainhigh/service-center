package com.shengsu.website.home.util;

import com.alibaba.fastjson.JSON;
import com.shengsu.util.StringUtil;
import com.shengsu.website.home.entity.SuccessCase;
import com.shengsu.website.home.po.SuccessCasePagePo;
import com.shengsu.website.home.po.SuccessCasePreviousPo;
import com.shengsu.website.home.po.SuccessCaseQueryPo;
import com.shengsu.website.home.po.SuccessCaseRelevantPo;
import com.shengsu.website.home.vo.SuccessCaseCreateVo;
import com.shengsu.website.home.vo.SuccessCaseListPageVo;
import com.shengsu.website.home.vo.SuccessCaseUpdateVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
public class SuccessCaseUtils {

    public static SuccessCase toSuccessCase(SuccessCaseCreateVo successCaseCreateVo) {
        if (successCaseCreateVo != null) {
            SuccessCase successCase = new SuccessCase();
            successCase.setLegendOssId(successCaseCreateVo.getLegendOssId());
            successCase.setType(successCaseCreateVo.getType());
            successCase.setTitle(successCaseCreateVo.getTitle());
            successCase.setCaseDate(successCaseCreateVo.getCaseDate());
            successCase.setSummary(successCaseCreateVo.getSummary());
            successCase.setSource(successCaseCreateVo.getSource());
            successCase.setAuthor(successCaseCreateVo.getAuthor());
            successCase.setContent(successCaseCreateVo.getContent());
            successCase.setIsHomeShow(successCaseCreateVo.getIsHomeShow());

            List<Long> relevantIdList = successCaseCreateVo.getRelevantIdList();
            if(relevantIdList!=null&&!relevantIdList.isEmpty()){
                successCase.setRelevant(JSON.toJSONString(relevantIdList));
            }

            return successCase;
        }
        return null;
    }

    public static SuccessCase toSuccessCase(SuccessCaseUpdateVo successCaseUpdateVo) {
        if (successCaseUpdateVo != null) {
            SuccessCase successCase = new SuccessCase();
            successCase.setId(successCaseUpdateVo.getId());
            successCase.setLegendOssId(successCaseUpdateVo.getLegendOssId());
            successCase.setType(successCaseUpdateVo.getType());
            successCase.setTitle(successCaseUpdateVo.getTitle());
            successCase.setCreateTime(successCaseUpdateVo.getCaseDate());
            successCase.setSummary(successCaseUpdateVo.getSummary());
            successCase.setSource(successCaseUpdateVo.getSource());
            successCase.setAuthor(successCaseUpdateVo.getAuthor());
            successCase.setContent(successCaseUpdateVo.getContent());
            successCase.setCaseDate(successCaseUpdateVo.getCaseDate());
            successCase.setIsHomeShow(successCaseUpdateVo.getIsHomeShow());
            List<Long> relevantIdList = successCaseUpdateVo.getRelevantIdList();
            if(relevantIdList!=null&&!relevantIdList.isEmpty()){
                successCase.setRelevant(JSON.toJSONString(relevantIdList));
            }
            return successCase;
        }
        return null;
    }

    public static SuccessCaseQueryPo toSuccessCaseQueryVo(SuccessCase successCase) {
        if (successCase != null) {
            SuccessCaseQueryPo successCaseQueryPo = new SuccessCaseQueryPo();
            successCaseQueryPo.setId(successCase.getId());
            successCaseQueryPo.setType(successCase.getType());
            successCaseQueryPo.setTitle(successCase.getTitle());
            successCaseQueryPo.setCaseDate(successCase.getCaseDate());
            successCaseQueryPo.setSummary(successCase.getSummary());
            successCaseQueryPo.setSource(successCase.getSource());
            successCaseQueryPo.setAuthor(successCase.getAuthor());
            successCaseQueryPo.setContent(successCase.getContent());
            successCaseQueryPo.setLegendOssId(successCase.getLegendOssId());
            successCaseQueryPo.setIsHomeShow(successCase.getIsHomeShow());
            return successCaseQueryPo;
        }
        return null;
    }

    public static SuccessCase toSuccessCase(SuccessCaseListPageVo successCaseListPageVo) {
        if (successCaseListPageVo != null) {
            SuccessCase successCase = new SuccessCase();
            successCase.setType(successCaseListPageVo.getType());
            successCase.setTitle(StringUtil.ToLikeStr(successCaseListPageVo.getTitle()));
            successCase.setSource(successCaseListPageVo.getSource());
            successCase.setAuthor(successCaseListPageVo.getAuthor());
            successCase.setPage(successCaseListPageVo.getPage());
            successCase.setPageSize(successCaseListPageVo.getPageSize());
            return successCase;
        }
        return null;
    }

    public static SuccessCase toSuccessCase(Date caseDate, Integer type) {
        if (caseDate != null) {
            SuccessCase successCase = new SuccessCase();
            successCase.setCaseDate(caseDate);
            successCase.setType(type);
            return successCase;
        }
        return null;
    }

    public static List<SuccessCaseRelevantPo> toNewsCenterRelevantPo(List<SuccessCase> successCases) {

        if (successCases != null && !successCases.isEmpty()) {
            List<SuccessCaseRelevantPo> newsCenterRelevantPos = new ArrayList<>();
            SuccessCaseRelevantPo successCaseRelevantPo = null;
            for (SuccessCase successCase :
                    successCases) {
                successCaseRelevantPo = new SuccessCaseRelevantPo();
                successCaseRelevantPo.setId(successCase.getId());
                successCaseRelevantPo.setTitle(successCase.getTitle());
                successCaseRelevantPo.setCaseDate(successCase.getCaseDate());
                newsCenterRelevantPos.add(successCaseRelevantPo);
            }

            return newsCenterRelevantPos;
        }

        return null;
    }

    public static SuccessCasePreviousPo toSuccessCasePreviousPo(SuccessCase successCase) {
        if (successCase != null) {
            SuccessCasePreviousPo successCasePreviousPo = new SuccessCasePreviousPo();
            successCasePreviousPo.setId(successCase.getId());
            successCasePreviousPo.setTitle(successCase.getTitle());
            return successCasePreviousPo;
        }
        return null;
    }

    public static List<SuccessCasePagePo>  toSuccessCasePagePo(List<SuccessCase> successCases) {

        if (successCases != null && !successCases.isEmpty()) {
            List<SuccessCasePagePo> newsCenterPagePos = new ArrayList<>();
            SuccessCasePagePo successCasePagePo = null;
            for (SuccessCase successCase :
                    successCases) {
                successCasePagePo = new SuccessCasePagePo();
                successCasePagePo.setId(successCase.getId());
                successCasePagePo.setTitle(successCase.getTitle());
                successCasePagePo.setCaseDate(successCase.getCaseDate());
                successCasePagePo.setSummary(successCase.getSummary());
                successCasePagePo.setLegendOssId(successCase.getLegendOssId());
                newsCenterPagePos.add(successCasePagePo);
            }

            return newsCenterPagePos;
        }
        return null;
    }
}
