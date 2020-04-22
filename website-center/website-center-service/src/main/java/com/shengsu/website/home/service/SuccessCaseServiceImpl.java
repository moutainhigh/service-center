package com.shengsu.website.home.service;

import com.alibaba.fastjson.JSONArray;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.home.constant.NewsCenterConst;
import com.shengsu.website.home.entity.SuccessCase;
import com.shengsu.website.home.mapper.SuccessCaseMapper;
import com.shengsu.website.home.po.*;
import com.shengsu.website.home.util.SuccessCaseUtils;
import com.shengsu.website.home.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zyc on 2019/9/17.
 */
@Service(value = "successCaseService")
public class SuccessCaseServiceImpl extends BaseServiceImpl implements SuccessCaseService {

    @Autowired
    private SuccessCaseMapper successCaseMapper;
    @Autowired
    private OssService ossService;

    @Override
    public BaseMapper getBaseMapper() {
        return successCaseMapper;
    }

    @Override
    public ResultBean create(SuccessCaseCreateVo successCaseCreateVo) {
        SuccessCase successCase = successCaseMapper.selectByTitle(successCaseCreateVo.getTitle());
        if (successCase != null) {
            return ResultUtil.formResult(false, ResultCode.CASE_DATA_REPEAT, null);
        }

        successCase = SuccessCaseUtils.toSuccessCase(successCaseCreateVo);
        successCaseMapper.save(successCase);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }


    @Override
    public ResultBean remove(SuccessCaseDeleteVo successCaseDeleteVo) {
        Long id = successCaseDeleteVo.getId();
        SuccessCase successCase = successCaseMapper.selectById(id);
        if (successCase == null) {
            return ResultUtil.formResult(false, ResultCode.CASE_ID_ERROR, null);
        }

        successCaseMapper.delete(id);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean edit(SuccessCaseUpdateVo successCaseUpdateVo) {
        Long id = successCaseUpdateVo.getId();
        SuccessCase successCase = successCaseMapper.selectById(id);
        if (successCase == null) {
            return ResultUtil.formResult(false, ResultCode.CASE_ID_ERROR, null);
        }

        successCase = successCaseMapper.selectByTitle(successCaseUpdateVo.getTitle());
        if (successCase != null && !successCase.getId().equals(id)) {
            return ResultUtil.formResult(false, ResultCode.CASE_DATA_REPEAT, null);
        }

        successCase = SuccessCaseUtils.toSuccessCase(successCaseUpdateVo);
        successCaseMapper.update(successCase);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean<SuccessCaseQueryPo> query(SuccessCaseQueryVo successCaseQueryVo) {
        Long id = successCaseQueryVo.getId();
        SuccessCase successCase = successCaseMapper.selectById(id);
        if (successCase == null) {
            return ResultUtil.formResult(false, ResultCode.NEWS_ID_ERROR, null);
        }
        SuccessCaseQueryPo successCaseQueryPo = SuccessCaseUtils.toSuccessCaseQueryVo(successCase);
        successCaseQueryPo.setLegendOssUrl(ossService.getUrl(OssConstant.OSS_SUCCESS_CASE_FFILEDIR,successCase.getLegendOssId()));
        String relevant = successCase.getRelevant();
        if (StringUtils.isNoneBlank(relevant)) {
            List<Long> relevantIdList = JSONArray.parseArray(relevant, Long.class);
            List<SuccessCase> successCases = successCaseMapper.selectRelevantByIds(relevantIdList);
            List<SuccessCaseRelevantPo> successCaseRelevantPos = SuccessCaseUtils.toNewsCenterRelevantPo(successCases);
            successCaseQueryPo.setRelevantNewCenterList(successCaseRelevantPos);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, successCaseQueryPo);
    }

    @Override
    public ResultBean listPage(SuccessCaseListPageVo successCaseListPageVo) {
        SuccessCase successCase = SuccessCaseUtils.toSuccessCase(successCaseListPageVo);
        Integer count = successCaseMapper.countByPage(successCase);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (count > 0) {
            List<SuccessCase> successCases = successCaseMapper.selectByPage(successCase);
            if(null == successCases || successCases.size() == 0){
                return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
            }
            List<SuccessCasePagePo> successCasePagePos = SuccessCaseUtils.toSuccessCasePagePo(successCases);
            getDetailUrls(successCasePagePos);
            resultMap.put(CommonConst.ROOT, successCasePagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean getDetails(NewsCenterDetailsVo newsCenterUpdateVo) {
        Long id = newsCenterUpdateVo.getId();
        Integer type = newsCenterUpdateVo.getType();
        ResultBean<SuccessCaseQueryPo> queryResult = query(new SuccessCaseQueryVo(id));
        if (!queryResult.isSuccess()) {
            return ResultUtil.formResult(false, ResultCode.CASE_ID_ERROR, null);
        }

        SuccessCaseQueryPo successCase = queryResult.getBody();
        SuccessCaseDetailsPo successCaseDetailsPo = new SuccessCaseDetailsPo();
        successCaseDetailsPo.setSuccessCase(successCase);

        //获取上一篇
        Date caseDate = successCase.getCaseDate();
        SuccessCase previousSuccessCase = successCaseMapper.selectPreviousByIdToType(SuccessCaseUtils.toSuccessCase(caseDate, type));
        SuccessCasePreviousPo successCasePreviousPo = SuccessCaseUtils.toSuccessCasePreviousPo(previousSuccessCase);
        successCaseDetailsPo.setPreviousSuccessCase(successCasePreviousPo);

        //获取下一篇
        SuccessCase nextSuccessCase = successCaseMapper.selectNextByIdToType(SuccessCaseUtils.toSuccessCase(caseDate, type));
        SuccessCasePreviousPo successCaseNextPo = SuccessCaseUtils.toSuccessCasePreviousPo(nextSuccessCase);
        successCaseDetailsPo.setNextSuccessCase(successCaseNextPo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, successCaseDetailsPo);
    }

    @Override
    public ResultBean getHomeShow() {
        List<SuccessCase> successCases = successCaseMapper.selectByIsHomeShow(NewsCenterConst.HOME_SHOW_YES);
        List<SuccessCasePagePo> successCasePagePos = SuccessCaseUtils.toSuccessCasePagePo(successCases);
        getDetailUrls(successCasePagePos);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, successCasePagePos);
    }

    public void getDetailUrls(List<SuccessCasePagePo> successCasePagePos){
        if (successCasePagePos != null) {
            List<String> list = new ArrayList<>();
            for (SuccessCasePagePo successCasePagePo :
                    successCasePagePos) {
                String legendOssId = successCasePagePo.getLegendOssId();
                list.add(legendOssId);
            }
            Map<String, String>  map = ossService.getUrls(OssConstant.OSS_SUCCESS_CASE_FFILEDIR,list);
            for (SuccessCasePagePo successCasePagePo :
                    successCasePagePos) {
                successCasePagePo.setLegendOssUrl(map.get(successCasePagePo.getLegendOssId()));
            }
        }
    }
}