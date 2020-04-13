package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.mapper.LawKnowledgeMapper;
import com.shengsu.website.bdapp.po.*;
import com.shengsu.website.bdapp.service.LawKnowledgeService;
import com.shengsu.website.bdapp.util.LawKnowledgeUtils;
import com.shengsu.website.bdapp.vo.LawKnowledgeDetailsVo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;
import com.shengsu.website.home.po.NewsCenterPagePo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 18:03
 **/
@Service("lawKnowledgeService")
public class LawKnowledgeServiceImpl extends BaseServiceImpl<LawKnowledge, String> implements LawKnowledgeService {
    @Autowired
    private OssService ossService;
    @Autowired
    private LawKnowledgeMapper lawKnowledgeMapper;
    @Override
    public BaseMapper<LawKnowledge, String> getBaseMapper() {
        return lawKnowledgeMapper;
    }

    @Override
    public ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo) {
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeListPageVo);
        Integer count = lawKnowledgeMapper.countAll(lawKnowledge);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listByPage(lawKnowledge);
            if(null == lawKnowledges || lawKnowledges.size() == 0){
                return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
            }
            List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
            resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public int updatePv() {
        return lawKnowledgeMapper.updatePv();
    }

    @Override
    public ResultBean getDetails(LawKnowledgeDetailsVo lawKnowledgeDetailsVo) {
        String knowledgeId = lawKnowledgeDetailsVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        //获取当前
        LawKnowledgeCurrentPo lawKnowledgeCurrentPo = LawKnowledgeUtils.toLawKnowledgeCurrentPo(lawKnowledge);
        lawKnowledgeCurrentPo.setPictureOssUrl(ossService.getUrl(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,lawKnowledge.getPictureOssId()));
        LawKnowledgeDetailsPo lawKnowledgeDetailsPo = new LawKnowledgeDetailsPo();
        lawKnowledgeDetailsPo.setLawKnowledgeCurrentPo(lawKnowledgeCurrentPo);

        //获取上一篇
        Date dateTime = lawKnowledge.getDateTime();
        String thirdCategoryId = lawKnowledge.getThirdCategoryId();
        LawKnowledge paramLawKnowledge = LawKnowledgeUtils.toLawKnowledge(dateTime,thirdCategoryId);
        LawKnowledge previousLawKnowledge = lawKnowledgeMapper.selectPreviousLawKnowledge(paramLawKnowledge);
        LawKnowledgePreviousPo lawKnowledgePreviousPo = LawKnowledgeUtils.toLawKnowledgePreviousPo(previousLawKnowledge);
        lawKnowledgeDetailsPo.setLawKnowledgePreviousPo(lawKnowledgePreviousPo);

        // 获取下一篇
        LawKnowledge nextLawKnowledge = lawKnowledgeMapper.selectNextLawKnowledge(paramLawKnowledge);
        LawKnowledgeNextPo lawKnowledgeNextPo = LawKnowledgeUtils.toLawKnowledgeNextPo(nextLawKnowledge);
        lawKnowledgeDetailsPo.setLawKnowledgeNextPo(lawKnowledgeNextPo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeDetailsPo);
    }

    @Override
    public ResultBean getlatestThreeCount() {
        List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.getlatestThreeCount();
        List<LawKnowledgeSimplePo> lawKnowledgeSimplePos = LawKnowledgeUtils.toLawKnowledgeSimplePos(lawKnowledges);
        if (null !=lawKnowledgeSimplePos && lawKnowledgeSimplePos.size()>0){
            for (LawKnowledgeSimplePo lawKnowledgeSimplePo : lawKnowledgeSimplePos) {
                String pictureOssId = lawKnowledgeSimplePo.getPictureOssId();
                if (StringUtils.isNoneBlank(pictureOssId)) {
                    lawKnowledgeSimplePo.setPictureOssUrl(ossService.getUrl(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,pictureOssId));
                }
            }
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeSimplePos);
    }
}