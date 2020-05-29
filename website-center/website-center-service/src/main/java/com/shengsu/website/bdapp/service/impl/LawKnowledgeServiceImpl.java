package com.shengsu.website.bdapp.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
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
import com.shengsu.website.bdapp.vo.*;
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
    public ResultBean create(LawKnowledgeCreateVo lawKnowledgeCreateVo) {
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByTitle(lawKnowledgeCreateVo.getTitle());
        if (lawKnowledge != null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_DATA_REPEAT, null);
        }

        lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeCreateVo);
        lawKnowledge.setKnowledgeId(UUID.randomUUID().toString());
        lawKnowledgeMapper.save(lawKnowledge);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean remove(LawKnowledgeDeleteVo lawKnowledgeDeleteVo) {
        String knowledgeId= lawKnowledgeDeleteVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }

        lawKnowledgeMapper.delete(knowledgeId);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean edit(LawKnowledgeUpdateVo lawKnowledgeUpdateVo) {
        String knowledgeId= lawKnowledgeUpdateVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        lawKnowledge = lawKnowledgeMapper.selectByTitle(lawKnowledgeUpdateVo.getTitle());
        if (lawKnowledge != null && !lawKnowledge.getKnowledgeId().equals(knowledgeId)) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_DATA_REPEAT, null);
        }
        lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeUpdateVo);
        lawKnowledgeMapper.update(lawKnowledge);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean query(LawKnowledgeQueryVo lawKnowledgeQueryVo) {
        String knowledgeId= lawKnowledgeQueryVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        LawKnowledgeQueryPo lawKnowledgeQueryPo = LawKnowledgeUtils.toLawKnowledgeQueryPo(lawKnowledge);
        lawKnowledgeQueryPo.setFirstCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getFirstCategoryId()));
        lawKnowledgeQueryPo.setSecondCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getSecondCategoryId()));
        lawKnowledgeQueryPo.setThirdCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledge.getThirdCategoryId()));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeQueryPo);
    }

    @Override
    public ResultBean listKnowledgeByPage(LawKnowledgeListByPageVo lawKnowledgeListByPageVo) {
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeListByPageVo);
        Integer count = lawKnowledgeMapper.countAll(lawKnowledge);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listByPage(lawKnowledge);
            List<LawKnowledgePagePo> lawKnowledgePagePos = LawKnowledgeUtils.toLawKnowledgePagePos(lawKnowledges);
            if (CollectionUtils.isNotEmpty(lawKnowledgePagePos)) {
                for (LawKnowledgePagePo lawKnowledgePagePo : lawKnowledgePagePos) {
                    lawKnowledgePagePo.setFirstCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledgePagePo.getFirstCategoryId()));
                    lawKnowledgePagePo.setSecondCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledgePagePo.getSecondCategoryId()));
                    lawKnowledgePagePo.setThirdCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(lawKnowledgePagePo.getThirdCategoryId()));
                }
            }
            resultMap.put(CommonConst.ROOT, lawKnowledgePagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
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
        String firstCategoryId = lawKnowledge.getFirstCategoryId();
        String secondCategoryId = lawKnowledge.getSecondCategoryId();
        String thirdCategoryId = lawKnowledge.getThirdCategoryId();
        //获取当前
        LawKnowledgeCurrentPo lawKnowledgeCurrentPo = LawKnowledgeUtils.toLawKnowledgeCurrentPo(lawKnowledge);
        lawKnowledgeCurrentPo.setFirstCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(firstCategoryId));
        lawKnowledgeCurrentPo.setSecondCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(secondCategoryId));
        lawKnowledgeCurrentPo.setThirdCategoryName(lawKnowledgeCategoryService.getNameByCategoryId(thirdCategoryId));
        lawKnowledgeCurrentPo.setPictureOssUrl(getNoRepeatRandom(1).get(0).toString());
        LawKnowledgeDetailsPo lawKnowledgeDetailsPo = new LawKnowledgeDetailsPo();
        lawKnowledgeDetailsPo.setLawKnowledgeCurrentPo(lawKnowledgeCurrentPo);

        //获取上一篇
        LawKnowledge paramLawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledge);
        // 先判断此分类属于哪级分类  前一篇的话就是找时间最挨近的最小的,后一篇就找时间最挨近的最大的
        //当前分类属于第三级分类
        LawKnowledge previousLawKnowledge = null;
        if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())){
            // 先获取当前分类下的上一篇,如果为空,获取二级分类下的知识文库,如果还未空 获取一级分类下的知识文库
             previousLawKnowledge = lawKnowledgeMapper.selectThirdPreviousLawKnowledge(paramLawKnowledge);
            if (null == previousLawKnowledge){
                // 三级分类下为空,获取二级分类下的文章
                 previousLawKnowledge = lawKnowledgeMapper.selectSecondPreviousLawKnowledge(paramLawKnowledge);
                 if (null == previousLawKnowledge){
                     // 二级分类为空,获取一级分类下的文章
                     previousLawKnowledge = lawKnowledgeMapper.selectFirstPreviousLawKnowledge(paramLawKnowledge);
                 }
            }
        }
        // 当前分类属于二级分类
        if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())){
            // 先获取当前分类下的上一篇,如果未空 获取一级分类下的知识文库
             previousLawKnowledge = lawKnowledgeMapper.selectSecondPreviousLawKnowledge(paramLawKnowledge);
            if (null == previousLawKnowledge){
                // 二级分类为空,获取一级分类下的文章
                previousLawKnowledge = lawKnowledgeMapper.selectFirstPreviousLawKnowledge(paramLawKnowledge);
            }

        }
        // 当前分类属于一级分类
        if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isBlank(lawKnowledge.getSecondCategoryId())&&StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())){
            previousLawKnowledge = lawKnowledgeMapper.selectFirstPreviousLawKnowledge(paramLawKnowledge);
        }
        LawKnowledgePreviousPo lawKnowledgePreviousPo = LawKnowledgeUtils.toLawKnowledgePreviousPo(previousLawKnowledge);
        lawKnowledgeDetailsPo.setLawKnowledgePreviousPo(lawKnowledgePreviousPo);
        // 获取下一篇
        //当前分类属于第三级分类
        LawKnowledge nextLawKnowledge = null;
        if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())){
            // 先获取当前分类下的上一篇,如果为空,获取二级分类下的知识文库,如果还未空 获取一级分类下的知识文库
            nextLawKnowledge = lawKnowledgeMapper.selectThirdNextLawKnowledge(paramLawKnowledge);
            if (null == nextLawKnowledge){
                // 三级分类下为空,获取二级分类下的文章
                nextLawKnowledge = lawKnowledgeMapper.selectSecondNextLawKnowledge(paramLawKnowledge);
                if (null == nextLawKnowledge){
                    // 二级分类为空,获取一级分类下的文章
                    nextLawKnowledge = lawKnowledgeMapper.selectFirstNextLawKnowledge(paramLawKnowledge);
                }
            }
        }
        // 当前分类属于二级分类
        if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())){
            // 先获取当前分类下的上一篇,如果未空 获取一级分类下的知识文库
            nextLawKnowledge = lawKnowledgeMapper.selectSecondNextLawKnowledge(paramLawKnowledge);
            if (null == nextLawKnowledge){
                // 二级分类为空,获取一级分类下的文章
                nextLawKnowledge = lawKnowledgeMapper.selectFirstNextLawKnowledge(paramLawKnowledge);
            }

        }
        // 当前分类属于一级分类
        if(StringUtils.isBlank(lawKnowledge.getThirdCategoryId())&& StringUtils.isBlank(lawKnowledge.getSecondCategoryId())&&StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())){
            nextLawKnowledge = lawKnowledgeMapper.selectFirstNextLawKnowledge(paramLawKnowledge);
        }
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
    public ResultBean getRandomArticles() {
        List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.getAll();
        Collections.shuffle(lawKnowledges);
        List<LawKnowledge> result = new ArrayList<>();
        for (int i=0;i<lawKnowledges.size();i++){
            if(i >= LAW_HEADLINES_RANDOM_COUNT)
                break;
            result.add(lawKnowledges.get(i));
        }
        List<LawKnowledgePo> lawKnowledgePos = LawKnowledgeUtils.toLawknowledgePO(result);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgePos);
    }
}
