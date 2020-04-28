package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
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
import org.apache.commons.lang3.StringUtils;
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
        String firstCategoryId = lawKnowledgeListPageVo.getFirstCategoryId();
        String secondCategoryId = lawKnowledgeListPageVo.getSecondCategoryId();
        String thirdCategoryId = lawKnowledgeListPageVo.getThirdCategoryId();
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeListPageVo);
        Map<String, Object> resultMap = new HashMap<>();
        // 三级类目不为空
        if (StringUtils.isNotBlank(thirdCategoryId)){
            // 包含三级数据不为空,三级数据为空,一级分类和二级分类为空,二级分类不为空
            Integer count = lawKnowledgeMapper.countThirdNotNull(lawKnowledge);
            if (count > 0) {
                List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listPageThirdNotNull(lawKnowledge);
                if(null == lawKnowledges || lawKnowledges.size() == 0){
                    return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
                }
                List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
                resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
                resultMap.put(CommonConst.TOTAL_COUNT, count);
            }
        }
        // 三级类目数据为空,二级类目不为空
        if (StringUtils.isBlank(thirdCategoryId) && StringUtils.isNotBlank(secondCategoryId)){
            // 包含一级分类和二级分类为空和二级分类不为空
            Integer count = lawKnowledgeMapper.countSecondNotNull(lawKnowledge);
            if (count > 0) {
                List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listPageSecondNotNull(lawKnowledge);
                if(null == lawKnowledges || lawKnowledges.size() == 0){
                    return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
                }
                List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
                resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
                resultMap.put(CommonConst.TOTAL_COUNT, count);
            }
        }
        // 二级分类为空,一级分类不为空
        if (StringUtils.isBlank(secondCategoryId)){
            // 包含 一级分类所有的
            Integer count = lawKnowledgeMapper.countFirstNotNull(lawKnowledge);
            if (count > 0) {
                List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listPageFirstNotNull(lawKnowledge);
                if(null == lawKnowledges || lawKnowledges.size() == 0){
                    return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
                }
                List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
                resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
                resultMap.put(CommonConst.TOTAL_COUNT, count);
            }
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
        //获取当前
        LawKnowledgeCurrentPo lawKnowledgeCurrentPo = LawKnowledgeUtils.toLawKnowledgeCurrentPo(lawKnowledge);
        lawKnowledgeCurrentPo.setFirstCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getFirstCategoryId()));
        lawKnowledgeCurrentPo.setSecondCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getSecondCategoryId()));
        lawKnowledgeCurrentPo.setThirdCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getThirdCategoryId()));
        lawKnowledgeCurrentPo.setPictureOssUrl(getNoRepeatRandom(1).get(0).toString());
        LawKnowledgeDetailsPo lawKnowledgeDetailsPo = new LawKnowledgeDetailsPo();
        lawKnowledgeDetailsPo.setLawKnowledgeCurrentPo(lawKnowledgeCurrentPo);

        //获取上一篇
        LawKnowledge paramLawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledge);
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
        List<LawKnowledgeCategory> lawKnowledgeCategories = new ArrayList<>();
        for (LawKnowledge lawKnowledge : lawKnowledges){
            if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())){
                // 三级分类不为空,获取知识库类目
                LawKnowledgeCategory lawKnowledgeCategory = lawKnowledgeCategoryService.getByCategoryId(lawKnowledge.getThirdCategoryId());
                lawKnowledgeCategories.add(lawKnowledgeCategory);
            }else if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())){
                // // 三级分类为空,二级分类不为空 获取知识库类目
                LawKnowledgeCategory lawKnowledgeCategory = lawKnowledgeCategoryService.getByCategoryId(lawKnowledge.getSecondCategoryId());
                lawKnowledgeCategories.add(lawKnowledgeCategory);
            }else if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isBlank(lawKnowledge.getSecondCategoryId())&&StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())){
                // // 三级分类为空,二级分类为空,一级分类不为空 获取知识库类目
                LawKnowledgeCategory lawKnowledgeCategory = lawKnowledgeCategoryService.getByCategoryId(lawKnowledge.getFirstCategoryId());
                lawKnowledgeCategories.add(lawKnowledgeCategory);
            }
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
