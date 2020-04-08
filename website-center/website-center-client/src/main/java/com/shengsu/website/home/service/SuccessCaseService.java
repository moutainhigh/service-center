package com.shengsu.website.home.service;


import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.home.vo.*;

/**
 * Created by zyc on 2019/9/17.
 */
public interface SuccessCaseService extends BaseService{
    ResultBean create(SuccessCaseCreateVo successCaseCreateVo);

    ResultBean remove(SuccessCaseDeleteVo successCaseDeleteVo);

    ResultBean edit(SuccessCaseUpdateVo successCaseUpdateVo);

    ResultBean query(SuccessCaseQueryVo successCaseQueryVo);

    ResultBean listPage(SuccessCaseListPageVo successCaseListPageVo);

    ResultBean getDetails(NewsCenterDetailsVo newsCenterUpdateVo);

    ResultBean getHomeShow();
}
