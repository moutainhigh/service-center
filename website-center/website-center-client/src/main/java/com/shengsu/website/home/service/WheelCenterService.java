package com.shengsu.website.home.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.home.vo.*;

/**
 * Created by zyc on 2019/9/17.
 */
public interface WheelCenterService  extends BaseService{
    ResultBean create(WheelCenterCreateVo wheelCenterCreateVo);

    ResultBean remove(WheelCenterDeleteVo wheelCenterDeleteVo);

    ResultBean edit(WheelCenterUpdateVo wheelCenterUpdateVo);

    ResultBean query(WheelCenterQueryVo wheelCenterQueryVo);

    ResultBean list(WheelCenterListVo wheelCenterListVo);
}
