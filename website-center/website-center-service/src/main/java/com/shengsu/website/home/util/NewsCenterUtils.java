package com.shengsu.website.home.util;

import com.alibaba.fastjson.JSON;
import com.shengsu.util.StringUtil;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.po.NewsCenterPagePo;
import com.shengsu.website.home.po.NewsCenterPreviousPo;
import com.shengsu.website.home.po.NewsCenterQueryPo;
import com.shengsu.website.home.po.NewsCenterRelevantPo;
import com.shengsu.website.home.vo.NewsCenterCreateVo;
import com.shengsu.website.home.vo.NewsCenterListPageVo;
import com.shengsu.website.home.vo.NewsCenterUpdateVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
public class NewsCenterUtils {

    public static NewsCenter toNewsCenter(NewsCenterCreateVo newsCenterCreateVo) {
        if (newsCenterCreateVo != null) {
            NewsCenter newsCenter = new NewsCenter();
            newsCenter.setType(newsCenterCreateVo.getType());
            newsCenter.setTitle(newsCenterCreateVo.getTitle());
            newsCenter.setNewsDate(newsCenterCreateVo.getNewsDate());
            newsCenter.setSummary(newsCenterCreateVo.getSummary());
            newsCenter.setSource(newsCenterCreateVo.getSource());
            newsCenter.setAuthor(newsCenterCreateVo.getAuthor());
            newsCenter.setContent(newsCenterCreateVo.getContent());
            newsCenter.setIsHomeShow(newsCenterCreateVo.getIsHomeShow());
            List<Long> relevantIdList = newsCenterCreateVo.getRelevantIdList();
            if(relevantIdList!=null&&!relevantIdList.isEmpty()){
                newsCenter.setRelevant(JSON.toJSONString(relevantIdList));
            }
            newsCenter.setPictureOssId(newsCenterCreateVo.getPictureOssId());
            newsCenter.setAscription(newsCenterCreateVo.getAscription());
            return newsCenter;
        }
        return null;
    }

    public static NewsCenter toNewsCenter(NewsCenterUpdateVo newsCenterUpdateVo) {
        if (newsCenterUpdateVo != null) {
            NewsCenter newsCenter = new NewsCenter();
            newsCenter.setId(newsCenterUpdateVo.getId());
            newsCenter.setType(newsCenterUpdateVo.getType());
            newsCenter.setTitle(newsCenterUpdateVo.getTitle());
            newsCenter.setNewsDate(newsCenterUpdateVo.getNewsDate());
            newsCenter.setSummary(newsCenterUpdateVo.getSummary());
            newsCenter.setSource(newsCenterUpdateVo.getSource());
            newsCenter.setAuthor(newsCenterUpdateVo.getAuthor());
            newsCenter.setContent(newsCenterUpdateVo.getContent());
            newsCenter.setIsHomeShow(newsCenterUpdateVo.getIsHomeShow());
            List<Long> relevantIdList = newsCenterUpdateVo.getRelevantIdList();
            if(relevantIdList!=null&&!relevantIdList.isEmpty()){
                newsCenter.setRelevant(JSON.toJSONString(relevantIdList));
            }
            newsCenter.setPictureOssId(newsCenterUpdateVo.getPictureOssId());
            newsCenter.setAscription(newsCenterUpdateVo.getAscription());

            return newsCenter;
        }
        return null;
    }

    public static NewsCenterQueryPo toNewsCenterQueryPo(NewsCenter newsCenter) {
        if (newsCenter != null) {
            NewsCenterQueryPo newsCenterQueryPo = new NewsCenterQueryPo();
            newsCenterQueryPo.setId(newsCenter.getId());
            newsCenterQueryPo.setType(newsCenter.getType());
            newsCenterQueryPo.setTitle(newsCenter.getTitle());
            newsCenterQueryPo.setNewsDate(newsCenter.getNewsDate());
            newsCenterQueryPo.setSummary(newsCenter.getSummary());
            newsCenterQueryPo.setSource(newsCenter.getSource());
            newsCenterQueryPo.setAuthor(newsCenter.getAuthor());
            newsCenterQueryPo.setContent(newsCenter.getContent());
            newsCenterQueryPo.setIsHomeShow(newsCenter.getIsHomeShow());
            newsCenterQueryPo.setPictureOssId(newsCenter.getPictureOssId());
            newsCenterQueryPo.setAscription(newsCenter.getAscription());
            return newsCenterQueryPo;
        }
        return null;
    }

    public static NewsCenter toNewsCenter(NewsCenterListPageVo newsCenterListPageVo) {
        if (newsCenterListPageVo != null) {
            NewsCenter newsCenter = new NewsCenter();
            newsCenter.setType(newsCenterListPageVo.getType());
            newsCenter.setTitle(StringUtil.ToLikeStr(newsCenterListPageVo.getTitle()));
            newsCenter.setSource(newsCenterListPageVo.getSource());
            newsCenter.setAuthor(newsCenterListPageVo.getAuthor());
            newsCenter.setAscription(newsCenterListPageVo.getAscription());
            newsCenter.setPage(newsCenterListPageVo.getPage());
            newsCenter.setPageSize(newsCenterListPageVo.getPageSize());
            return newsCenter;
        }
        return null;
    }

    public static NewsCenter toNewsCenter(Date newsDate, Integer type,String ascription) {
        if (newsDate != null) {
            NewsCenter newsCenter = new NewsCenter();
            newsCenter.setNewsDate(newsDate);
            newsCenter.setType(type);
            newsCenter.setAscription(ascription);
            return newsCenter;
        }
        return null;
    }

    public static List<NewsCenterRelevantPo> toNewsCenterRelevantPo(List<NewsCenter> newsCenters) {

        if (newsCenters != null && !newsCenters.isEmpty()) {
            List<NewsCenterRelevantPo> newsCenterRelevantPos = new ArrayList<>();
            NewsCenterRelevantPo newsCenterRelevantPo = null;
            for (NewsCenter newsCenter :
                    newsCenters) {
                newsCenterRelevantPo = new NewsCenterRelevantPo();
                newsCenterRelevantPo.setId(newsCenter.getId());
                newsCenterRelevantPo.setTitle(newsCenter.getTitle());
                newsCenterRelevantPo.setNewsDate(newsCenter.getNewsDate());
                newsCenterRelevantPos.add(newsCenterRelevantPo);
            }

            return newsCenterRelevantPos;
        }

        return null;
    }

    public static NewsCenterPreviousPo toNewsCenterPreviousPo(NewsCenter newsCenter) {
        if (newsCenter != null) {
            NewsCenterPreviousPo newsCenterPreviousPo = new NewsCenterPreviousPo();
            newsCenterPreviousPo.setId(newsCenter.getId());
            newsCenterPreviousPo.setTitle(newsCenter.getTitle());
            return newsCenterPreviousPo;
        }
        return null;
    }

    public static List<NewsCenterPagePo> toNewsCenterPagePo(List<NewsCenter> newsCenters) {

        if (newsCenters != null && !newsCenters.isEmpty()) {
            List<NewsCenterPagePo> newsCenterPagePos = new ArrayList<>();
            NewsCenterPagePo newsCenterPagePo = null;
            for (NewsCenter newsCenter :
                    newsCenters) {
                newsCenterPagePo = new NewsCenterPagePo();
                newsCenterPagePo.setId(newsCenter.getId());
                newsCenterPagePo.setTitle(newsCenter.getTitle());
                newsCenterPagePo.setNewsDate(newsCenter.getNewsDate());
                newsCenterPagePo.setSummary(newsCenter.getSummary());
                newsCenterPagePo.setPictureOssId(newsCenter.getPictureOssId());
                newsCenterPagePo.setAscription(newsCenter.getAscription());
                newsCenterPagePos.add(newsCenterPagePo);
            }

            return newsCenterPagePos;
        }
        return null;
    }
}
