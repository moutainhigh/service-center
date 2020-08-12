package com.shengsu.website.home.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.MQEnum;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.MQProducerService;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.home.constant.NewsCenterConst;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.mapper.NewsCenterMapper;
import com.shengsu.website.home.po.*;
import com.shengsu.website.home.util.NewsCenterUtils;
import com.shengsu.website.home.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.shengsu.website.app.constant.BizConst.OPERATE_TYPE_CREATE;
import static com.shengsu.website.app.constant.BizConst.OPERATE_TYPE_UPDATE;

/**
 * Created by zyc on 2019/9/17.
 */
@Service(value = "newsCenterService")
public class NewsCenterServiceImpl extends BaseServiceImpl<NewsCenter, String> implements NewsCenterService{
    @Autowired
    private OssService ossService;
    @Autowired
    private NewsCenterMapper newsCenterMapper;
    @Autowired
    private MQProducerService mqProducerService;

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
        String newsId = UUID.randomUUID().toString();
        newsCenter = NewsCenterUtils.toNewsCenter(newsCenterCreateVo);
        newsCenter.setNewsId(newsId);
        newsCenterMapper.save(newsCenter);
        // 发送ES mq消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newsId",newsId);
        jsonObject.put("operateType",OPERATE_TYPE_CREATE);
        mqProducerService.send(MQEnum.ELASTICSEARCH_NEWS_CENTER, JSON.toJSONString(jsonObject));
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
        String newsId = newsCenter.getNewsId();
        newsCenter = newsCenterMapper.selectByTitle(newsCenterUpdateVo.getTitle());
        if (newsCenter != null && !newsCenter.getId().equals(id)) {
            return ResultUtil.formResult(false, ResultCode.NEWS_DATA_REPEAT, null);
        }

        newsCenter = NewsCenterUtils.toNewsCenter(newsCenterUpdateVo);
        newsCenterMapper.update(newsCenter);
        // 发送ES mq消息(es中保存一份)
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newsId",newsId);
        jsonObject.put("title",newsCenter.getTitle());
        jsonObject.put("content",newsCenter.getContent());
        jsonObject.put("operateType",OPERATE_TYPE_UPDATE);
        mqProducerService.send(MQEnum.ELASTICSEARCH_NEWS_CENTER, JSON.toJSONString(jsonObject));
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
            List<String> list = newsCenterPagePos.stream().map(NewsCenterPagePo::getPictureOssId).collect(Collectors.toList());
            Map<String, String>  map = ossService.getUrls(OssConstant.OSS_NEWS_CENTERR_FFILEDIR,list);
            for (NewsCenterPagePo newsCenterPagePo : newsCenterPagePos) {
                newsCenterPagePo.setPictureOssUrl(map.get(newsCenterPagePo.getPictureOssId()));
            }
        }
    }

}