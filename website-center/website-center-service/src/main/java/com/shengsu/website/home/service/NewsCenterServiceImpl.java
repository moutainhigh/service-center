package com.shengsu.website.home.service;

import com.alibaba.fastjson.JSONArray;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.home.entity.LawcaseConsultAppendix;
import com.shengsu.website.home.po.ConsultAppendixDetailsPo;
import com.shengsu.website.home.util.LawcaseConsultAppendixUtils;
import com.shengsu.website.home.constant.NewsCenterConst;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.mapper.LawcaseConsultAppendixMapper;
import com.shengsu.website.home.mapper.NewsCenterMapper;
import com.shengsu.website.home.po.*;
import com.shengsu.website.home.util.NewsCenterUtils;
import com.shengsu.website.home.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zyc on 2019/9/17.
 */
@Service(value = "newsCenterService")
public class NewsCenterServiceImpl extends BaseServiceImpl implements NewsCenterService {
    @Autowired
    private OssService ossService;
    @Autowired
    private NewsCenterMapper newsCenterMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return newsCenterMapper;
    }

    @Override
    public ResultBean create(NewsCenterCreateVo newsCenterCreateVo) {

        NewsCenter newsCenter = newsCenterMapper.selectByTitle(newsCenterCreateVo.getTitle());
        if (newsCenter != null) {
            return ResultUtil.formResult(false, ResultCode.NEWS_DATA_REPEAT, null);
        }

        newsCenter = NewsCenterUtils.toNewsCenter(newsCenterCreateVo);
        newsCenterMapper.save(newsCenter);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }


    @Override
    public ResultBean remove(NewsCenterDeleteVo newsCenterCreateVo) {
        Long id = newsCenterCreateVo.getId();
        NewsCenter newsCenter = newsCenterMapper.selectById(id);
        if (newsCenter == null) {
            return ResultUtil.formResult(false, ResultCode.NEWS_ID_ERROR, null);
        }

        newsCenterMapper.delete(id);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean edit(NewsCenterUpdateVo newsCenterUpdateVo) {
        Long id = newsCenterUpdateVo.getId();
        NewsCenter newsCenter = newsCenterMapper.selectById(id);
        if (newsCenter == null) {
            return ResultUtil.formResult(false, ResultCode.NEWS_ID_ERROR, null);
        }

        newsCenter = newsCenterMapper.selectByTitle(newsCenterUpdateVo.getTitle());
        if (newsCenter != null && !newsCenter.getId().equals(id)) {
            return ResultUtil.formResult(false, ResultCode.NEWS_DATA_REPEAT, null);
        }

        newsCenter = NewsCenterUtils.toNewsCenter(newsCenterUpdateVo);
        newsCenterMapper.update(newsCenter);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean<NewsCenterQueryPo> query(NewsCenterQueryVo newsCenterUpdateVo) {
        Long id = newsCenterUpdateVo.getId();
        NewsCenter newsCenter = newsCenterMapper.selectById(id);
        if (newsCenter == null) {
            return ResultUtil.formResult(false, ResultCode.NEWS_ID_ERROR, null);
        }
        NewsCenterQueryPo newsCenterQueryPo = NewsCenterUtils.toNewsCenterQueryPo(newsCenter);
        newsCenterQueryPo.setPictureOssUrl(ossService.getUrl(OssConstant.OSS_NEWS_CENTERR_FFILEDIR,newsCenter.getPictureOssId()));

        String relevant = newsCenter.getRelevant();
        if (StringUtils.isNoneBlank(relevant)) {
            List<Long> relevantIdList = JSONArray.parseArray(relevant, Long.class);
            List<NewsCenter> newsCenters = newsCenterMapper.selectRelevantByIds(relevantIdList);
            List<NewsCenterRelevantPo> newsCenterRelevantPos = NewsCenterUtils.toNewsCenterRelevantPo(newsCenters);
            newsCenterQueryPo.setRelevantNewCenterList(newsCenterRelevantPos);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, newsCenterQueryPo);
    }

    @Override
    public ResultBean listPage(NewsCenterListPageVo newsCenterListPageVo) {
        NewsCenter newsCenter = NewsCenterUtils.toNewsCenter(newsCenterListPageVo);
        Integer count = newsCenterMapper.countByPage(newsCenter);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<NewsCenter> newsCenters = newsCenterMapper.selectByPage(newsCenter);
            List<NewsCenterPagePo> newsCenterPagePos = NewsCenterUtils.toNewsCenterPagePo(newsCenters);
            getDetailUrls(newsCenterPagePos);
            resultMap.put(CommonConst.ROOT, newsCenterPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean getDetails(NewsCenterDetailsVo newsCenterUpdateVo) {
        Long id = newsCenterUpdateVo.getId();
        Integer type = newsCenterUpdateVo.getType();
        ResultBean<NewsCenterQueryPo> queryResult = query(new NewsCenterQueryVo(id));
        if (!queryResult.isSuccess()) {
            return ResultUtil.formResult(false, ResultCode.NEWS_ID_ERROR, null);
        }

        NewsCenterQueryPo newsCenter = queryResult.getBody();
        NewsCenterDetailsPo newsCenterDetailsPo = new NewsCenterDetailsPo();
        newsCenterDetailsPo.setNewsCenter(newsCenter);

        //获取上一篇
        Date newsDate = newsCenter.getNewsDate();
        String ascription = newsCenter.getAscription();
        NewsCenter previousNewsCenter = newsCenterMapper.selectPreviousByIdToType(NewsCenterUtils.toNewsCenter(newsDate, type,ascription)
        );
        NewsCenterPreviousPo newsCenterPreviousPo = NewsCenterUtils.toNewsCenterPreviousPo(previousNewsCenter);
        newsCenterDetailsPo.setPreviousNewCenter(newsCenterPreviousPo);

        //获取下一篇
        NewsCenter nextNewsCenter = newsCenterMapper.selectNextByIdToType(NewsCenterUtils.toNewsCenter(newsDate, type,ascription));
        NewsCenterPreviousPo newsCenterNextPo = NewsCenterUtils.toNewsCenterPreviousPo(nextNewsCenter);
        newsCenterDetailsPo.setNextNewCenter(newsCenterNextPo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, newsCenterDetailsPo);
    }


    @Override
    public ResultBean getHomeShow(String ascription) {
        NewsCenter newsCenter = new NewsCenter();
        newsCenter.setIsHomeShow(NewsCenterConst.HOME_SHOW_YES);
        newsCenter.setAscription(ascription);
        List<NewsCenter> newsCenters = newsCenterMapper.getNewsCenter(newsCenter);
        List<NewsCenterPagePo> newsCenterPagePos = NewsCenterUtils.toNewsCenterPagePo(newsCenters);
        getDetailUrls(newsCenterPagePos);
        return  ResultUtil.formResult(true, ResultCode.SUCCESS, newsCenterPagePos);
    }

    public void getDetailUrls(List<NewsCenterPagePo> newsCenterPagePos){
        if (null !=newsCenterPagePos && newsCenterPagePos.size()>0) {
            List<String> list = new ArrayList<>();
            for (NewsCenterPagePo newsCenterPagePo : newsCenterPagePos) {
                String pictureOssId = newsCenterPagePo.getPictureOssId();
                list.add(pictureOssId);
            }
            Map<String, String>  map = ossService.getUrls(OssConstant.OSS_NEWS_CENTERR_FFILEDIR,list);
            for (NewsCenterPagePo newsCenterPagePo : newsCenterPagePos) {
                newsCenterPagePo.setPictureOssUrl(map.get(newsCenterPagePo.getPictureOssId()));
            }
        }
    }

    /**
     * @description:
     * @author: lipiao
     * @create: 2019-12-05 09:26
     **/
    @Service(value = "lawcaseConsultAppendixService")
    public static class LawcaseConsultAppendixServiceImpl extends BaseServiceImpl implements LawcaseConsultAppendixService {
        @Autowired
        private LawcaseConsultAppendixMapper lawcaseConsultAppendixMapper;
        @Autowired
        private OssService ossService;

        @Override
        public BaseMapper<LawcaseConsultAppendix, String> getBaseMapper() {
            return lawcaseConsultAppendixMapper;
        }

        @Override
        public int deleteByRefId(String receivableId) {
            return lawcaseConsultAppendixMapper.deleteByRefId(receivableId);
        }

        @Override
        public ResultBean<List<ConsultAppendixDetailsPo>> getDetails(String refId){
            List<LawcaseConsultAppendix> appendixList = lawcaseConsultAppendixMapper.selectByRefId(refId);
            if(appendixList==null||appendixList.isEmpty()){
                return ResultUtil.formResult(false, ResultCode.EXCEPTION_CONSULT_APPENDIX_REFID_IS_NULL);
            }

            List<ConsultAppendixDetailsPo> appendixDetailsPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsPos(appendixList);
            getDetaIlsOssUrl(appendixDetailsPos);
            return ResultUtil.formResult(true,ResultCode.SUCCESS,appendixDetailsPos);
        }
        @Override
        public ResultBean<List<ConsultAppendixDetailsPo>> getDetailsListByPage(List<String> refIds){
            List<LawcaseConsultAppendix> appendixList = lawcaseConsultAppendixMapper.selectByRefIdList(refIds);
            if(appendixList==null||appendixList.isEmpty()){
                return ResultUtil.formResult(false, ResultCode.EXCEPTION_CONSULT_APPENDIX_REFID_IS_NULL);
            }

            List<ConsultAppendixDetailsPo> appendixDetailsPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsPos(appendixList);
            getDetaIlsOssUrl(appendixDetailsPos);
            return ResultUtil.formResult(true,ResultCode.SUCCESS,appendixDetailsPos);
        }
        private void getDetaIlsOssUrl(List<ConsultAppendixDetailsPo> appendixDetailsPos){
            List<String> list = new ArrayList<>();
            for (ConsultAppendixDetailsPo appendix:
            appendixDetailsPos) {
                String url = appendix.getOssResourceId();
                list.add(url);
            }
            Map<String, String>  map = ossService.getUrls(OssConstant.OSS_WEBSITE_CENTER_FFILEDIR,list);
                for(ConsultAppendixDetailsPo appendix:
                        appendixDetailsPos){
                        appendix.setOssResourceUrl(map.get(appendix.getOssResourceId()));
                }
        }
    }
}