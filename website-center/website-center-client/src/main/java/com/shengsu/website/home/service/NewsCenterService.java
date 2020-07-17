package com.shengsu.website.home.service;


import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.home.entity.NewsCenter;
import com.shengsu.website.home.vo.*;
import com.shengsu.website.market.entity.LawKnowledge;

/**
 * Created by zyc on 2019/9/17.
 */
public interface NewsCenterService extends BaseService<NewsCenter,String>{
    ResultBean create(NewsCenterCreateVo newsCenterCreateVo);

    ResultBean remove(NewsCenterDeleteVo newsCenterCreateVo);

    ResultBean edit(NewsCenterUpdateVo newsCenterUpdateVo);

    ResultBean query(NewsCenterQueryVo newsCenterUpdateVo);

    ResultBean listPage(NewsCenterListPageVo newsCenterUpdateVo);

    ResultBean getDetails(NewsCenterDetailsVo newsCenterUpdateVo);

    ResultBean getHomeShow(String ascription);
}
