package com.shengsu.website.market.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.OssService;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.po.NewsCenterDetailsPo;
import com.shengsu.website.home.po.NewsCenterPreviousPo;
import com.shengsu.website.home.po.NewsCenterQueryPo;
import com.shengsu.website.home.util.NewsCenterUtils;
import com.shengsu.website.home.vo.NewsCenterQueryVo;
import com.shengsu.website.market.entity.LawDoc;
import com.shengsu.website.market.mapper.LawDocMapper;
import com.shengsu.website.market.po.LawDocDetailsPo;
import com.shengsu.website.market.po.LawDocListPagePo;
import com.shengsu.website.market.po.LawDocPreviousPo;
import com.shengsu.website.market.po.LawDocQueryPo;
import com.shengsu.website.market.service.LawDocService;
import com.shengsu.website.market.util.LawDocUtils;
import com.shengsu.website.market.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.shengsu.website.app.constant.BizConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-13 17:03
 **/
@Service("lawDocService")
public class LawDocServiceImpl extends BaseServiceImpl<LawDoc, String> implements LawDocService {
    @Autowired
    private OssService ossService;
    @Autowired
    private SystemDictService systemDictService;
    @Autowired
    private LawDocMapper lawDocMapper;
    @Override
    public BaseMapper<LawDoc, String> getBaseMapper() {
        return lawDocMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean create(LawDocCreateVo lawDocCreateVo) {
        LawDoc lawDoc = lawDocMapper.selectByDocName(lawDocCreateVo.getDocName());
        if (lawDoc != null) {
            return ResultUtil.formResult(false, ResultCode.LAW_DOC_DATA_REPEAT, null);
        }
        lawDoc = LawDocUtils.toLawDoc(lawDocCreateVo);
        lawDoc.setDocId(UUID.randomUUID().toString());
        lawDocMapper.save(lawDoc);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean remove(LawDocDeleteVo lawDocDeleteVo) {
        String docId = lawDocDeleteVo.getDocId();
        LawDoc lawDoc = lawDocMapper.selectByDocId(docId);
        if (lawDoc == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_DOC_ID_ERROR, null);
        }

        lawDocMapper.softDelete(docId);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean edit(LawDocUpdateVo lawDocUpdateVo) {
        String docId = lawDocUpdateVo.getDocId();
        LawDoc lawDoc = lawDocMapper.selectByDocId(docId);
        if (lawDoc == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_DOC_ID_ERROR, null);
        }
        lawDoc = lawDocMapper.selectByDocName(lawDocUpdateVo.getDocName());
        if (lawDoc != null) {
            return ResultUtil.formResult(false, ResultCode.LAW_DOC_DATA_REPEAT, null);
        }
        lawDoc =  LawDocUtils.toLawDoc(lawDocUpdateVo);
        lawDocMapper.update(lawDoc);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean<LawDocQueryPo> query(LawDocQueryVo lawDocQueryVo) {
        String docId = lawDocQueryVo.getDocId();
        LawDoc lawDoc = lawDocMapper.selectByDocId(docId);
        if (lawDoc == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_DOC_ID_ERROR, null);
        }
        LawDocQueryPo lawDocQueryPo = LawDocUtils.toLawDocQueryPo(lawDoc);
        lawDocQueryPo.setOssResourceUrl(ossService.getUrl(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,lawDoc.getOssResourceId()));
        SystemDict systemDict = systemDictService.getOneByDisplayValue(DICT_CODE_DOC_TYPE, lawDoc.getDocType());
        lawDocQueryPo.setDocTypeStr(systemDict==null?"":systemDict.getDisplayName());
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawDocQueryPo);
    }

    @Override
    public ResultBean ListLawDocByPage(ListLawDocByPageVo listLawDocByPageVo) {
        LawDoc lawDoc = LawDocUtils.toLawDoc(listLawDocByPageVo);
        Integer count = lawDocMapper.countLawDocByPage(lawDoc);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<LawDoc> lawDocs = lawDocMapper.selectLawDocByPage(lawDoc);
            List<LawDocListPagePo> lawDocListPagePos = LawDocUtils.toLawDocListPagePos(lawDocs);
            fillLawDocListPagePoData(lawDocListPagePos);
            resultMap.put(CommonConst.ROOT, lawDocListPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean getDetails(LawDocDetailsVo lawDocDetailsVo) {
        String docId = lawDocDetailsVo.getDocId();
        LawDocQueryVo lawDocQueryVo = new LawDocQueryVo();
        lawDocQueryVo.setDocId(docId);
        ResultBean<LawDocQueryPo> queryResult = query(lawDocQueryVo);
        if (!queryResult.isSuccess()) {
            return ResultUtil.formResult(false, ResultCode.LAW_DOC_ID_ERROR, null);
        }
        // 当前文档
        LawDocQueryPo lawDoc = queryResult.getBody();
        LawDocDetailsPo lawDocDetailsPo = new LawDocDetailsPo();
        lawDocDetailsPo.setLawDoc(lawDoc);
        //获取上一篇
        Date uploadTime = lawDoc.getUploadTime();
        String docType = lawDoc.getDocType();
        LawDoc previousLawDoc = lawDocMapper.selectPreviousLawDoc(LawDocUtils.toLawDoc(uploadTime,docType));
        LawDocPreviousPo lawDocPreviousPo = LawDocUtils.toLawDocPreviousPo(previousLawDoc);
        lawDocDetailsPo.setPreviousLawDocPo(lawDocPreviousPo);
        //获取下一篇
        LawDoc nextLawDoc = lawDocMapper.selectNextLawDoc(LawDocUtils.toLawDoc(uploadTime,docType));
        LawDocPreviousPo lawDocNextPo = LawDocUtils.toLawDocPreviousPo(nextLawDoc);
        lawDocDetailsPo.setNextLawDocPo(lawDocNextPo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawDocDetailsPo);
    }

    @Override
    public ResultBean updateDownloads(String docId) {
        lawDocMapper.updateDownloads(docId);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    private void fillLawDocListPagePoData(List<LawDocListPagePo> lawDocListPagePos) {
        if (CollectionUtils.isNotEmpty(lawDocListPagePos)) {
            List<String> ossResourceIds = new ArrayList<>();
            for (LawDocListPagePo lawDocListPagePo : lawDocListPagePos) {
                String ossResourceId = lawDocListPagePo.getOssResourceId();
                ossResourceIds.add(ossResourceId);
            }
            Map<String, String>  map = ossService.getUrls(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,ossResourceIds);
            Map<String, SystemDict> systemDictMap = systemDictService.mapByDictCode(DICT_CODE_DOC_TYPE);
            for (LawDocListPagePo lawDocListPagePo : lawDocListPagePos) {
                lawDocListPagePo.setOssResourceUrl(map.get(lawDocListPagePo.getOssResourceId()));
                lawDocListPagePo.setDocTypeStr(systemDictMap.get(lawDocListPagePo.getDocType())==null?"":systemDictMap.get(lawDocListPagePo.getDocType()).getDisplayName());
            }
        }
    }

}
