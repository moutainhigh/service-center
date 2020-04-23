package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.entity.LawKnowledgeCategory;
import com.shengsu.website.bdapp.mapper.LawKnowledgeMapper;
import com.shengsu.website.bdapp.po.*;
import com.shengsu.website.bdapp.service.LawKnowledgeCategoryService;
import com.shengsu.website.bdapp.service.LawKnowledgeService;
import com.shengsu.website.bdapp.util.LawKnowledgeCategoryUtils;
import com.shengsu.website.bdapp.util.LawKnowledgeUtils;
import com.shengsu.website.bdapp.vo.LawKnowledgeDetailsVo;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.shengsu.website.app.constant.BizConst.LAW_HEADLINES_RANDOM_COUNT;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 18:03
 **/
@Service("lawKnowledgeService")
public class LawKnowledgeServiceImpl extends BaseServiceImpl<LawKnowledge, String> implements LawKnowledgeService {
    @Value("${lawknowledge.picture-range}")
    private int pictureRange;
    @Autowired
    private LawKnowledgeCategoryService lawKnowledgeCategoryService;
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
    public int updatePv(String knowledgeId) {
        return lawKnowledgeMapper.updatePv(knowledgeId);
    }

    @Override
    public ResultBean getDetails(LawKnowledgeDetailsVo lawKnowledgeDetailsVo) {
        String knowledgeId = lawKnowledgeDetailsVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        String thirdCategoryId = lawKnowledge.getThirdCategoryId();
        //获取当前
        LawKnowledgeCurrentPo lawKnowledgeCurrentPo = LawKnowledgeUtils.toLawKnowledgeCurrentPo(lawKnowledge);
        lawKnowledgeCurrentPo.setFirstCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getFirstCategoryId()));
        lawKnowledgeCurrentPo.setSecondCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getSecondCategoryId()));
        lawKnowledgeCurrentPo.setThirdCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getThirdCategoryId()));
        lawKnowledgeCurrentPo.setPictureOssUrl(getNoRepeatRandom(1).get(0).toString());
        LawKnowledgeDetailsPo lawKnowledgeDetailsPo = new LawKnowledgeDetailsPo();
        lawKnowledgeDetailsPo.setLawKnowledgeCurrentPo(lawKnowledgeCurrentPo);

        //获取上一篇
        Date dateTime = lawKnowledge.getDateTime();
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
    public ResultBean getLatestThree() {
        List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.getLatestThree();
        List<String> thirdCategoryIds = new ArrayList<>();
        for (LawKnowledge lawKnowledge :lawKnowledges ){
            thirdCategoryIds.add(lawKnowledge.getThirdCategoryId());
        }
        List<LawKnowledgeCategory> lawKnowledgeCategories = new ArrayList<>();
        if (null !=thirdCategoryIds && thirdCategoryIds.size()>0){
            lawKnowledgeCategories = lawKnowledgeCategoryService.getManyByThirdCategoryIds(thirdCategoryIds);
        }
        Map<String,LawKnowledgeCategory> lawKnowledgeCategoryMap = LawKnowledgeCategoryUtils.toLawKnowledgeCategoryMap(lawKnowledgeCategories);
        List<Integer> itemList = getNoRepeatRandom(lawKnowledges.size());
        List<LawKnowledgeSimplePo> lawKnowledgeSimplePos = LawKnowledgeUtils.toLawKnowledgeSimplePos(lawKnowledges,lawKnowledgeCategoryMap,itemList);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeSimplePos);
    }
    /**
    * @Description: 获取指定范围内的随机不重复数
    * @Param: * @Param size: 
    * @Return: * @return: java.util.HashSet<java.lang.Integer>
    * @date: 
    */
    private List<Integer> getNoRepeatRandom(int size) {
        HashSet<Integer> hashSet = new HashSet<>();
        Integer i = 0;
        for (int j = 1; j != 0; j++) {
            i = (int) (Math.random() * pictureRange) + 1;
            hashSet.add(i);
            if (size == hashSet.size()) {
                break;
            }
        }
        return new ArrayList<>(hashSet);
    }

    @Override
    public ResultBean getRandomTitles() {
        List<String> titles = lawKnowledgeMapper.getAllTitle();
        Collections.shuffle(titles);
        List<String> result = new ArrayList<>();
        for (int i=0;i<titles.size();i++){
            if(i >= LAW_HEADLINES_RANDOM_COUNT) break;result.add(titles.get(i));
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }
}
